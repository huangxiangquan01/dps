package cn.xqhuang.dps;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class StringHttpDemo {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(build().getBytes());

            InputStream inputStream = socket.getInputStream();

            byte[] bytes = new byte[1024];
            int len;
            while ( (len= inputStream.read(bytes))!=-1){
                String strText = new String(bytes, 0, len);
                System.out.println(strText);
            }
        } catch (Exception e) {

        }
    }

    public static String build() {
        StringBuilder  sb = new StringBuilder();
        sb.append("GET /dps-servlet/hello HTTP/1.1\r\n");
        sb.append("Host 127.0.0.1:8080\r\n");

        sb.append("Connection: keep-alive\r\n");
        sb.append("CaChe-Control: max-age=0\r\n");
        sb.append("Upgrade-Insecure-Requests: 1\r\n");
        sb.append("User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36\r\n");
        sb.append("Sec-Fetch-Dest: document\r\n");
        sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n");
        sb.append("Sec-Fetch-Mode: navigate\r\n");
        sb.append("Sec-Fetch-Site: none\r\n");
        sb.append("Sec-Fetch-User: ?1\r\n");

        sb.append("Accept-Encoding: gzip, deflate, br\r\n");
        sb.append("Accept-Language: zh-CN,zh;q=0.9\r\n");
        sb.append("Content-Length: 3\r\n");
        sb.append("\r\n");
        sb.append("hello");

        return sb.toString();
    }
}
