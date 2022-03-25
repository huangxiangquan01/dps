package cn.xqhuang.dps.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {

    public static void main(String[] args) {
        try {
            // 创建NIO ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));
            // 设置ServerSocketChannel为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 打开Selector处理Channel，即创建epoll
            Selector selector = Selector.open();  //创建多路复用器

            // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  //将channel注册到多路复用器上

            while (true) {
                // 阻塞等待需要处理的事件发生
                selector.select();

                // 获取selector中注册的全部事件的 SelectionKey 实例
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 遍历SelectionKey对事件进行处理
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {  // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = channel.accept();
                        socketChannel.configureBlocking(false);
                        // 这里只注册了读事件，如果需要给客户端发送数据可以注册写事件
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println("客户端连接成功");
                    } else if (key.isReadable()) {  // 如果是OP_READ事件，则进行读取和打印
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                        int read = socketChannel.read(byteBuffer);
                        // 如果有数据，把数据打印出来
                        if (read > 0) {
                            System.out.println("接收到消息：" + new String(byteBuffer.array(), 0, read));


                            String respMsg = "BAD ORDER";
                            byte[] resp = respMsg.getBytes("utf-8");
                            ByteBuffer respBb = ByteBuffer.allocate(resp.length);
                            respBb.put(resp);
                            respBb.flip();

                            socketChannel.register(key.selector(), SelectionKey.OP_WRITE, respBb);
                        } else if (read == -1) { // 如果客户端断开连接，关闭Socket
                            System.out.println("客户端断开连接");
                            socketChannel.close();
                        }
                    } else if (key.isWritable()) {
                        ByteBuffer bb = (ByteBuffer) key.attachment();
                        if (bb.hasRemaining()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            sc.write(bb);
                        }
                    }
                    //从事件集合里删除本次处理的key，防止下次select重复处理
                    iterator.remove();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
