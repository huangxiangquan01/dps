package cn.xqhuang.dps.bio;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                System.out.println("-----------等待连接----------");

                //阻塞方法
                Socket accept = serverSocket.accept();
                System.out.println("-----------有客户端连接了---------");
                handler(accept);
            }
        } catch (IOException e) {

        }
    }

    public static void handler(Socket accept) throws IOException {
        System.out.println("-------------准备read---------");
        byte[] bytes = new byte[128];

        //接收客户端/服务端的数据，阻塞方法，没有数据可读时就阻塞
        int read = accept.getInputStream().read(bytes);
        System.out.println("-------------read完毕---------");
        if (read != -1) {
            System.out.println("------ 接收到客户端的数据: " + new String(bytes, 0 , read));

            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("收到".getBytes());
            outputStream.flush();
        }
    }
}
