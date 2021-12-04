package cn.xqhuang.dps.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpServerSocket {

    public static void main(String[] args) {
        ServerSocket server;
        Socket accept;
        try {
            // 监听指定的端口
            server = new ServerSocket(9001);
            // server将一直等待连接的到来
            while (true) {
                accept = server.accept();
                // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
                InputStream in = accept.getInputStream();

                byte[] bytes = new byte[1024];
                int len;
                StringBuilder sb = new StringBuilder();
                while ((len = in.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                }
                System.out.print(sb.toString() );
            }
        } catch (IOException e) {
            System.out.println("链接断开");
        }
    }
}
