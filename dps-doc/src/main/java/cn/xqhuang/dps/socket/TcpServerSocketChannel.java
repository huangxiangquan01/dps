package cn.xqhuang.dps.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TcpServerSocketChannel {

    static Set<SocketChannel> channels = new HashSet<>();

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9001));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select(1000);


            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                System.out.println("连接成功");

                channels.add(socketChannel);
            }

            Iterator<SocketChannel> iterator = channels.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();

                ByteBuffer bf = ByteBuffer.allocate(128);
                int read = sc.read(bf);

                if (read > 0) {
                    System.out.println(new String(bf.array()));
                } else if (read == -1) {
                    iterator.remove();
                }
            }
        }
    }
}
