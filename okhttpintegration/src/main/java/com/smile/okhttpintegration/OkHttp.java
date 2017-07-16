/*
 * Copyright (c) 2017 [zhiye.wei@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smile.okhttpintegration;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Smile Wei
 * @since 2017/04/10.
 */
public class OkHttp {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static int connectTimeOut = 10;
    private static int readTimeOut = 20;
    private static int writeTimeOut = 20;
    private static int retryCount = 0;

    private static String userAgent;
    static String errorCode = "error_code";

    private static OkHttpClient.Builder builder;

    public static OkHttpClient client() {
        return getInstance().build();
    }

    private static OkHttpClient.Builder init() {
        synchronized (OkHttp.class) {
            if (builder == null) {
                builder = new OkHttpClient.Builder();

                if (retryCount > 0)
                    builder.addInterceptor(new RetryInterceptor(retryCount));

                if (!TextUtils.isEmpty(userAgent))
                    builder.addInterceptor(new HeadInterceptor());

                if (BuildConfig.DEBUG) {
                    builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
                    builder.hostnameVerifier(new TrustAllHostnameVerifier());
                }
            }
        }
        return builder;
    }

    private static OkHttpClient.Builder getInstance() {
        return builder == null ? init() : builder;
    }

    /**
     * get方法
     *
     * @param url      url
     * @param params   参数
     * @param callback 回调函数
     * @return call
     */
    public static Call get(String url, Map<String, Object> params, OkCallback callback) {
        return get(url, params, null, callback);
    }

    /**
     * get方法
     *
     * @param url      url
     * @param params   参数
     * @param tag      标记位
     * @param callback 回调函数
     * @return call
     */
    public static Call get(String url, Map<String, Object> params, Object tag, OkCallback callback) {
        String endUrl = url + "?" + encodeParameters(params);

        Log.e("url", endUrl);

        Request.Builder builder = new Request.Builder().url(endUrl);
        if (tag != null) {
            builder.tag(tag);
        }

        Request request = builder.build();
        Call call = getInstance()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .build()
                .newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * post方法
     *
     * @param url      url
     * @param params   参数
     * @param callback 回调函数
     * @return call
     */
    public static Call post(String url, Map<String, Object> params, OkCallback callback) {
        return post(0, url, params, null, callback);
    }

    /**
     * post方法
     *
     * @param url      url
     * @param params   参数
     * @param callback 回调函数
     * @return call
     */
    public static Call post(int timeOut, String url, Map<String, Object> params, OkCallback callback) {
        return post(timeOut, url, params, null, callback);
    }

    /**
     * post方法
     *
     * @param url      url
     * @param json     json
     * @param callback 回调函数
     * @return call
     */
    public static Call postJson(String url, String json, OkCallback callback) {
        return postJson(url, json, null, callback);
    }

    /**
     * post方法
     *
     * @param url      url
     * @param params   参数
     * @param tag      标记位
     * @param callback 回调函数
     * @return call
     */
    public static Call post(int timeOut, String url, Map<String, Object> params, Object tag, OkCallback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if (tag != null) {
            builder.tag(tag);
        }

        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params == null) {
            params = new HashMap<>();
        }
        if (params.size() > 0) {
            for (String key : params.keySet()) {
                formBuilder.add(key, params.get(key) == null ? "" : String.valueOf(params.get(key)));
            }
        }
        builder.post(formBuilder.build());

        Request request = builder.build();
        Call call = getInstance()
                .readTimeout(timeOut == 0 ? readTimeOut : timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut == 0 ? writeTimeOut : timeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()
                .newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * postJson方法
     *
     * @param url      url
     * @param json     json
     * @param tag      标记位
     * @param callback 回调函数
     * @return call
     */
    public static Call postJson(String url, String json, Object tag, OkCallback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        if (tag != null) {
            builder.tag(tag);
        }

        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = builder.post(requestBody).build();
        Call call = getInstance()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()
                .newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 添加参数
     *
     * @param params 参数值
     * @return 参数字符串
     */
    private static String encodeParameters(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        StringBuilder sb = new StringBuilder("");
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue() == null ? "" : String.valueOf(entry.getValue()), "UTF-8"));
                sb.append('&');
            }
            return sb.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(
                    "Encoding not supported: " + "UTF-8", uee);
        }
    }

    private static class RetryInterceptor implements Interceptor {

        private int maxRetry;       //最大重试次数
        private int retryNum = 0;   //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                response = chain.proceed(request);
            }
            return response;
        }
    }

    private static class TrustAllCerts implements X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 添加头部User Agent
     */
    private static class HeadInterceptor implements Interceptor {

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .header("User-Agent", userAgent) // 标明发送本次请求的客户端
                    .build();
            return chain.proceed(request);
        }
    }


    public static void setConnectTimeOut(int connectTimeOut) {
        OkHttp.connectTimeOut = connectTimeOut;
    }

    public static void setReadTimeOut(int readTimeOut) {
        OkHttp.readTimeOut = readTimeOut;
    }

    public static void setWriteTimeOut(int writeTimeOut) {
        OkHttp.writeTimeOut = writeTimeOut;
    }

    public static void setRetryCount(int retryCount) {
        OkHttp.retryCount = retryCount;
    }

    public static void setUserAgent(String userAgent) {
        if (!TextUtils.isEmpty(userAgent))
            OkHttp.userAgent = userAgent;
    }

    public static void setErrorCode(String errorCode) {
        if (!TextUtils.isEmpty(errorCode))
            OkHttp.errorCode = errorCode;
    }

}
