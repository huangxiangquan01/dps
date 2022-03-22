package cn.xqhuang.dps.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AioClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9000)).get();

        socketChannel.write(ByteBuffer.wrap("HelloServer".getBytes()));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Integer integer = socketChannel.read(byteBuffer).get();

        if (integer > 0) {
            System.out.println("客户端收到信息：" + new String(byteBuffer.array(), 0, integer));
        }
    }
}
