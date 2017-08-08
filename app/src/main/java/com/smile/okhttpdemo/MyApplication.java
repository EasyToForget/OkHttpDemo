package com.smile.okhttpdemo;

import android.app.Application;

import com.smile.okhttpintegration.OkHttp;

/**
 * @author Smile Wei
 * @since 2017/8/8.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttp.setConnectTimeOut(30);
        OkHttp.setReadTimeOut(30);
        OkHttp.setWriteTimeOut(30);
        OkHttp.setUploadReadTimeOut(30);
        OkHttp.setUploadWriteTimeOut(30);
        //OkHttp.setRetryCount(4);
        //OkHttp.setErrorCode("setErrorCode");
        OkHttp.setErrorStatus("state", "0000");
        //OkHttp.setUserAgent("setUserAgent");
    }

}
