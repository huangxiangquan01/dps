package cn.xqhuang.dps.framework.protocol.http;

import cn.xqhuang.dps.framework.Invocation;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public String send(URL url, Invocation invocation) {
        HttpURLConnection urlConnection;
        ObjectOutputStream oos;
        try {
            // URL url = new URL("http", hostname, port, "");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            // urlConnection.setDoInput(true);

            OutputStream outputStream = urlConnection.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(invocation);
            oos.flush();

            InputStream inputStream = urlConnection.getInputStream();

            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            if (read > 0) {
                return new String(bytes, 0 , read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
