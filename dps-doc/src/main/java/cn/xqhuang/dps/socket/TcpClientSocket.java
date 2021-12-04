package cn.xqhuang.dps.socket;

import java.io.OutputStream;
import java.net.Socket;

public class TcpClientSocket {

    public static void main(String args[]) throws Exception {
        //
        Socket socket = new Socket("127.0.0.1", 9001);
        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();

        int i = 10;
        while (i > 0) {
            outputStream.write(("JSON"+ i).getBytes());
            i--;
        }
        socket.close();
        outputStream.close();
    }
}
