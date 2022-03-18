package cn.xqhuang.dps.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    static Set<SocketChannel> socketChannelSet = new HashSet<>();

    public static void main(String[] args) {
        try {
            // 创建NIO ServerSocketChannel,与BIO的serverSocket类似
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));
            // 设置ServerSocketChannel为非阻塞
            serverSocketChannel.configureBlocking(false);
            System.out.println("服务启动成功");
            while (true) {
                // 非阻塞模式accept方法不会阻塞，否则会阻塞
                // NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
                SocketChannel accept = serverSocketChannel.accept();
                if (accept != null) {
                    System.out.println("连接成功");
                    // 设置SocketChannel为非阻塞
                    accept.configureBlocking(false);
                    // 保存客户端连接在List中
                    socketChannelSet.add(accept);
                }
                // 遍历连接进行数据读取
                Iterator<SocketChannel> iterator = socketChannelSet.iterator();
                while (iterator.hasNext()) {
                    SocketChannel sc = iterator.next();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    // 非阻塞模式read方法不会阻塞，否则会阻塞
                    int read = sc.read(byteBuffer);
                    // 如果有数据，把数据打印出来
                    if (read > 0) {
                        System.out.println("接收到消息：" + new String(byteBuffer.array(), 0, read));
                    } else if (read == -1) { // 如果客户端断开，把socket从集合中去掉
                        iterator.remove();
                        System.out.println("客户端断开连接");
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
