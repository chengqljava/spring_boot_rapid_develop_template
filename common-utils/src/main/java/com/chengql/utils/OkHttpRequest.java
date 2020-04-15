package com.chengql.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType X_WWW_FORM = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");

    OkHttpClient client = new OkHttpClient();
    // volatile 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的
    private static volatile OkHttpRequest okHttpRequest = null;

    private OkHttpRequest() {

    }

    /**
     * 双检锁 双检锁，又叫双重校验锁，综合了懒汉式和饿汉式两者的优缺点整合而成。看上面代码实现中， 特点是在synchronized关键字内外都加了一层
     * if 条件判断，这样既保证了线程安全， 又比直接上锁提高了执行效率，还节省了内存空间
     */
    public static OkHttpRequest getInstance() {
        if (okHttpRequest == null) {
            synchronized (OkHttpRequest.class) {
                if (okHttpRequest == null) {
                    okHttpRequest = new OkHttpRequest();
                }
            }
        }
        return okHttpRequest;
    }

    /**
     * @param url
     * @return
     * @throws IOException
     *             get请求
     */
    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String getHeadParam(String url, Map<String, String> headerMap) throws IOException {
        Builder builder = new Request.Builder();
        headerParam(builder, headerMap);
        Request request = builder.url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * @param url
     * @param headerMap
     * @return
     * @throws IOException
     */
    public String getHeadForm(String url, Map<String, String> headerMap, Map<String, String> fromParam) throws IOException {
        Builder builder = new Request.Builder();
        headerParam(builder, headerMap);
        okhttp3.FormBody.Builder fromBodyBuilder = new FormBody.Builder();
        if (fromParam != null && !fromParam.isEmpty()) {
            // Lambda表达式，代码简单易懂
            fromParam.forEach((k, v) -> {
                fromBodyBuilder.add(k, v);
            });
        }
        Request request = builder.url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * @param url
     * @param json
     * @return
     * @throws IOException
     *             json类型请求参数
     */
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        /*
         * Call call = client.newCall(request); call.
         */

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * @param url
     * @param json
     * @param headerMap
     * @return
     * @throws IOException
     *             json类型请求参数 头部参数
     */
    public String postHeaderParam(String url, String json, Map<String, String> headerMap) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Builder builder = new Request.Builder();
        headerParam(builder, headerMap);
        Request request = builder.url(url).post(body).build();
        /*
         * Call call = client.newCall(request); call.
         */

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String postForm(String url, Map<String, String> fromParam) throws IOException {
        okhttp3.FormBody.Builder fromBodyBuilder = new FormBody.Builder();
        if (fromParam != null && !fromParam.isEmpty()) {
            // Lambda表达式，代码简单易懂
            fromParam.forEach((k, v) -> {
                fromBodyBuilder.add(k, v);
            });
        }
        RequestBody body = fromBodyBuilder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        /*
         * Call call = client.newCall(request); call.
         */

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String postHeaderForm(String url, Map<String, String> headerMap, Map<String, String> fromParam) throws IOException {
        okhttp3.FormBody.Builder fromBodyBuilder = new FormBody.Builder();
        formParam(fromBodyBuilder, fromParam);
        RequestBody body = fromBodyBuilder.build();
        Builder builder = new Request.Builder();
        headerParam(builder, headerMap);
        Request request = builder.url(url).post(body).build();
        /*
         * Call call = client.newCall(request); call.
         */

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * @param builder
     * @param headerMap
     *            header 参数初始化
     */
    private void headerParam(Builder builder, Map<String, String> headerMap) {
        if (headerMap != null && !headerMap.isEmpty()) {
            // Lambda表达式，代码简单易懂
            headerMap.forEach((k, v) -> {
                builder.addHeader(k, v);
            });
        }
    }

    /**
     * @param builder
     * @param fromParam
     *            参数初始化
     */
    private void formParam(okhttp3.FormBody.Builder fromBodyBuilder, Map<String, String> fromParam) {
        if (fromParam != null && !fromParam.isEmpty()) {
            // Lambda表达式，代码简单易懂
            fromParam.forEach((k, v) -> {
                if (StringUtils.isNoneBlank(v)) {
                    fromBodyBuilder.add(k, v);
                }
            });
        }
    }
}
