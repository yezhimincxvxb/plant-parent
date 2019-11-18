package com.moguying.plant.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public enum CurlUtil {
    INSTANCE;

    private final int connectTimeOut = 30000;

    public String jsonPostRequest(String requestUrl, String requestParam, String requestMethod) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(connectTimeOut);
            connection.setRequestMethod(requestMethod);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            return httpRequest(requestParam, connection);
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String httpRequest(String requestUrl, String requestParam, String requestMethod) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(connectTimeOut);
            connection.setRequestMethod(requestMethod);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            return httpRequest(requestParam, connection);
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String httpRequest(String requestParam, HttpURLConnection connection) throws IOException {

        connection.connect();
        if (null != requestParam) {
            OutputStream os = connection.getOutputStream();
            os.write(requestParam.getBytes("UTF-8"));
            os.close();
        }
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        return buffer.toString();

    }

}
