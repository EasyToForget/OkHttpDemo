package com.smile.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smile.okhttpintegration.OkCallback;
import com.smile.okhttpintegration.OkHttp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttp.setConnectTimeOut(5);
        OkHttp.setReadTimeOut(5);
        OkHttp.setWriteTimeOut(5);
        //OkHttp.setRetryCount(4);
        OkHttp.setErrorCode("setErrorCode");
        OkHttp.setUserAgent("setUserAgent");

        findViewById(R.id.btn_req).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttp.get("http://api.tuikexing.com", null, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                    }
                });
            }
        });

    }
}
