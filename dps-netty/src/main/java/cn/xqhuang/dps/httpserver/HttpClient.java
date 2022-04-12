package cn.xqhuang.dps.httpserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/4/811:02
 */
public class HttpClient {

    public static void main(String[] args) {
        try {
            URL url  = new URL("http", "localhost", 8090, "")  ;
            HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            urlConnection.setDoOutput(true);

            urlConnection.connect();

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write("Hello Server".getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = urlConnection.getResponseCode();

            System.out.println(responseCode);

            InputStream inputStream = urlConnection.getInputStream();
            byte[] bytes  = new byte[1024];
            int read = inputStream.read(bytes);
            if (read > 0) {
                System.out.println(new String(bytes, 0, read));
            }
        } catch (Exception e) {

        }

    }
}
