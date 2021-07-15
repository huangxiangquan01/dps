# 一、Tomcat处理请求过程
Tomcat通过Endpoint组件接收socket连接，接收到⼀个socket连接后会执⾏如下步骤
1. 第⼀次从socket中获取数据到InputBuffer中，BIO对应的是InternalInputBuffer，⽗类是 AbstractInputBuffer 
1. 然后基于InputBuffer进⾏解析数据 
1. 先解析请求⾏，把请求⽅法，请求uri，请求协议等封装到org.apache.coyote.Request对象中 
1. org.apache.coyote.Request中的属性都是MessageBytes类型，直接可以理解为字节类型，因为从 socket中获取的数据都是字节，在解析过程中不⽤直接把字节转成字符串，并且MessageBytes虽然表 示字节，但是它并不会真正的存储字节，还是使⽤ByteChunk基于InputBuffer中的字节数组来进⾏标 记，标记字节数组中的哪个⼀个范围表示请求⽅法，哪个⼀个范围表示请求uri等等。 
1. 然后解析头，和解析请求⾏类似 6. 解析完请求头后，就基于请求头来初始化⼀些参数，⽐如Connection是keepalive是close，⽐如是否 有Content-length，并且对于的⻓度是多少等等，还包括当前请求在处理请求体时应该使⽤哪个 InputFilter。 
1. 然后将请求交给容器 8. 容器再将请求交给具体的servlet进⾏处理 
1. servlet在处理请求的过程中会利⽤response进⾏响应，返回数据给客户端，⼀个普通的响应过程会把 数据先写⼊⼀个缓冲区，当调⽤flush，或者close⽅法时会把缓冲区中的内容发送给socet，下⾯有⼀ 篇单独的⽂章讲解tomcat响应请求过程 
1. servlet处理完请求后，先会检查是否需要把响应数据发送给socket 
1. 接着看当前请求的请求体是否处理结束，是否还有剩余数据，如果有剩余数据需要把这些数据处理掉， 以便能够获取到下⼀个请求的数据
1. 然后回到第⼀步开始处理下⼀个请求

# ⼆、Tomcat响应数据过程
当我们在Servlet中调⽤如下⽅法
```
    OutputStream outputStream = resp.getOutputStream(); 
    outputStream.write("test".getBytes());
```
resp对应的类型为ResponseFacade, 得到的outputStream的类型为CoyoteOutputStream。 所以响应数据是通过CoyoteOutputStream这个类处理的。 当调⽤outputStream的write⽅法写数据时，实际调⽤的就是CoyoteOutputStream类的write(byte[] b) ⽅法。

```
    @Override 
    public void write(byte[] b) throws IOException { 
        write(b, 0, b.length); 
    } 
    @Override 
    public void write(byte[] b, int off, int len) throws IOException { 
        ob.write(b, off, len); 
    }
```
在CoyoteOutputStream类中有⼀个属性是ob，类型为org.apache.catalina.connector.OutputBuffer ，该属性是在构造CoyoteOutputStream对象时初始化的。先注意OutputBuffer所在的包。

我们在调⽤write⽅法时，实际就是调⽤OutputBuffer的write⽅法，⽽write⽅法实际调⽤的就是该类中的 writeBytes(byte b[], int off, int len)：

```
private void writeBytes(byte b[], int off, int len)  throws IOException { 
     if (closed) { 
        return; 
     }

    bb.append(b, off, len); 
    bytesWritten += len; 

    // if called from within flush(), then immediately flush 
    // remaining bytes 

    if (doFlush) { 
        // 那么每次write都把缓冲中的数据发送出去 
        bb.flushBuffer(); 
    }
}
```
在OutputBuffer中有⼀个属性叫做bb，类型是ByteChunk。在Tomcat响应流程中，可以把ByteChunk类 当作⼀个缓冲区的实现，该类中有⼀个字节数组，名字叫做buff，默认⼤⼩为8192。

当我们在write字节数据时，就是把数据添加到ByteChunk对应的缓冲区buff中。当把数据添加到缓冲区 后，如果有其他线程在执⾏outputSteam的flush()⽅法，则doFlush为true，那么则会调⽤ bb.flushBuffer()。

在ByteChunk中有⼀个属性out，类型是ByteOutputChannel，它表示缓冲区中的数据该向流向哪个渠 道，为了⽅便理解，可以先理解为渠道就是socket，表示把缓冲区中的数据发送给socket，当实际情况并 不是，暂且这么理解。

ByteOutputChannel类中有⼀个⽅法realWriteBytes(byte buf[], int off, int len)，当调⽤ out.realWriteBytes(src, off, len)⽅法时，就会把src数据发送给对应驱动.

在当前这个ByteChunk中，它的out对应的仍然还是org.apache.catalina.connector.OutputBuffer，在 这个类中存在该⽅法：
```
public void realWriteBytes(byte buf[], int off, int cnt) throws IOException { 
    if (closed) { 
        return;
    } 
    if (coyoteResponse == null) {
        return; 
    } 
    // If we really have something to write 10 if (cnt > 0) { 
    // real write to the adapter 
    outputChunk.setBytes(buf, off, cnt);
    try { 
        coyoteResponse.doWrite(outputChunk); 
    } catch (IOException e) { 
        // An IOException on a write is almost always due to 
        // the remote client aborting the request. Wrap this
        // so that it can be handled better by the error disp atcher.throw new ClientAbortException(e); } 
    } 
}
```
该⽅法中通过⼀个outputChunk来标记数据，表示标记的这些数据是要发送给socket的。⽽真正的发送逻 辑交给了coyoteResponse.doWrite(outputChunk)来进⾏处理，coyoteResponse的类型为 org.apache.coyote.Response。

ByteChunk中有⼀个⽅法append，表示向缓冲区buff中添加数据，其中有⼀个逻辑，当缓冲区满了之后就 会调⽤out.realWriteBytes(src, off, len)，表示把缓冲区中的数据发送出去。缓存区的⼤⼩有⼀个限制， 可以修改，默认为8192。

还有⼀种情况就算缓冲区没有满，但是在write之前调⽤⽤过flush⽅法，那么本次write的数据会先放⼊缓 冲区，然后再把缓冲区中的数据发送出去。

当我们调⽤outputStream的flush⽅法时： 
1. 先判断是否发送过响应头，没有发送则先发送响应头 
1. 再调⽤ByteChunk的flushBuffer⽅法，把缓冲区中剩余的数据发送出去 
1. 因为上⽂中我们所理解的将缓冲区的数据发送出去，是直接发送给socket，但实际情况是把数据发送给 另外⼀个缓冲区，这个缓冲区也是⽤ByteChunk类实现的，名字叫做socketBuffer。所以当我们在使 ⽤flush⽅法时就需要把socketBuffer中的数据真正发送给socket。

接下来我们来看看coyoteResponse.doWrite(outputChunk);的具体实现细节。 该发⽅法实际调⽤的是outputBuffer.doWrite(chunk, this);这⾥的outputBuffer的类型是 InternalOutputBuffer，在执⾏doWrite⽅法时，调⽤的是⽗类AbstractOutputBuffer的doWrite⽅法。

该doWrite⽅法中，⾸先会判断响应头是否已经发送，如果没有发送，则会构造响应头，并发响应头发送给 socketBuffer，发送完响应头，会调⽤响应的output的activeFilters，对于不同的响应体需要使⽤不同的 发送逻辑。⽐如ChunkedOutputFilter是⽤来发送分块响应体的，IdentityOutputFilter是⽤来发送 Content-length响应体的，VoidOutputFilter不会真正的把数据发送出去。

在构造响应头时，会识别响应体应该通过什么OutputFilter来发送，如果响应中存在content-length那么 则使⽤IdentityOutputFilter来发送响应体，否则使⽤ChunkedOutputFilter，当然还有⼀些异常情况下会 使⽤VoidOutputFilter，表示不会发送响应体。

那现在的问题的，响应体的Content-length是在什么时候确定的？ 答案是：当请求在servlet中执⾏完成后，会调⽤response.finishResponse()⽅法，该⽅法会调⽤ outputBuffer.close()，该outputBuffer就是org.apache.catalina.connector.OutputBuffer，该⽅法会 判断响应体是否已发送，如果在调⽤这个close时响应头还没有发送，则表示响应体的数据在之前⼀直没有 发送过，⼀直存在了第⼀层缓冲区中，并且⼀直没有塞满该缓冲区，因为该缓冲区如果被塞满了，则会发 送响应头，所以当执⾏到close⽅法是，响应头还没发送过，那么缓冲区中的数据就是响应体全部的数据， 即，缓冲区数据的⻓度就是content-length。 反之，在调⽤close⽅法之前，就已经发送过数据了，那么响应头中就没有content-length，就会⽤ ChunkedOutputFilter来发送数据。

并且在执⾏close⽅法时，会先将响应头的数据发送给socketbuffer，然后将第⼀层缓冲区的数据通过对应 的OutputFilter发送给socketbuffer，然后调⽤OutputFilter的end⽅法，IdentityOutputFilter的end⽅ 法实现很简单，⽽ChunkedOutputFilter的end⽅法则相对做的事情更多⼀点，因为 ChunkedOutputFilter的doWrite⼀次只会发送⼀块数据，所以end要负责循环调⽤doWrite⽅法，把全部 的数据库发送完。

最后将socketbuffer中的数据发送给socket。