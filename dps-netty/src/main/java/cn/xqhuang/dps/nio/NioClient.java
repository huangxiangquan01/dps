package cn.xqhuang.dps.nio;
 
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
 
public class NioClient {
 
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);

        Selector selector = Selector.open();
		boolean isConnect = sc.connect(new InetSocketAddress("localhost", 9000));
		if (!isConnect) {
			sc.register(selector, SelectionKey.OP_CONNECT);
		}
 
        boolean isStop = false;
        while (true) {
			int count = selector.select();
			if (count == 0)
				continue;
            Iterator<SelectionKey> i = selector.selectedKeys().iterator();
            while (i.hasNext()) {
                SelectionKey sk = i.next();
				i.remove();
				if (sk.isValid()) {
					if (sk.isConnectable()) {
						if (sc.finishConnect()) {
							sc.register(selector, SelectionKey.OP_WRITE);
						} else {
							System.exit(1);
						}
					} else if (sk.isWritable()) {
						System.out.println("writing");
						byte[] req = "QUERY TIME ORDER".getBytes("utf-8");
						ByteBuffer bb = ByteBuffer.allocate(req.length);
						bb.put(req);
						bb.flip();
						sc.write(bb);

						sc.register(selector, SelectionKey.OP_READ);
					} else if (sk.isReadable()) {
						System.out.println("reading");
						ByteBuffer bb = ByteBuffer.allocate(1024);
						int readBytes = sc.read(bb);
						bb.flip();
						byte[] resp = new byte[readBytes];
						bb.get(resp);
						String respMsg = new String(resp, "utf-8");
						System.out.println("Now is " + respMsg);
						isStop = true;
						break;
					}
				}
            }
 
			if (isStop) {
				sc.close();
				break;
			}
        }
 
    }
 
}