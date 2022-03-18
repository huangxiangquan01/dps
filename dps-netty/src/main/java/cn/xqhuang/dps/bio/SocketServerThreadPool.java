package cn.xqhuang.dps.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerThreadPool {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while (true) {
                System.out.println("-----------等待连接----------");
                //阻塞方法
                Socket accept = serverSocket.accept();
                System.out.println("-----------有客户端连接了---------");
                new Thread(() -> {
                    try {
                        SocketServer.handler(accept);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {

        }
    }
}
