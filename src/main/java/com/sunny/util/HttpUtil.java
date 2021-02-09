package com.sunny.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * http组件
 *
 * @author ZJ
 */
public final class HttpUtil {

    /**
     * 请求地址
     */
    private final String url;

    /**
     * 请求类型
     */
    private final String method;

    /**
     * 请求头数据
     */
    private final Map<String, String> headers = new LinkedHashMap<>();

    /**
     * 构造函数
     *
     * @param url  url
     * @param type type
     */
    public HttpUtil(String url, String type) {
        this.url = url;
        this.method = type;

        // 默认请求头
        headers.put("accept", "*/*");
        headers.put("connection", "Keep-Alive");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        if (this.method.equals("POST")) {
            headers.put("content-type", "application/json");
        }
    }

    /**
     * 设置请求头
     *
     * @param key   键
     * @param value 值
     */
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * 发送请求
     *
     * @param data 请求数据
     * @return 响应结果
     */
    public String send(String data) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            URL url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(this.method);

            // 设置请求头
            Set<String> keys = this.headers.keySet();
            for (String key : keys) {
                httpURLConnection.setRequestProperty(key, this.headers.get(key));
            }

            // 开启输入输出
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // 输出数据
            if (!this.method.equals("GET")) {
                outputStream = httpURLConnection.getOutputStream();
                if (data != null) {
                    outputStream.write(data.getBytes());
                }
            }

            // 获取响应数据
            inputStream = httpURLConnection.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            int read = inputStream.read(bytes);
            System.out.println(read);

            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String send() {
        return this.send(null);
    }

}