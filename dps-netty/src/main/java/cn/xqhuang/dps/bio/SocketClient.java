package cn.xqhuang.dps.bio;

import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("localhost", 9000);

            //向服务端发送数据
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("123".getBytes());
            outputStream.flush();
            System.out.println("向服务端发送消息结束");

            //接收服务端回传的数据
            SocketServer.handler(socket);
        } catch (Exception e) {

        }

    }
}
