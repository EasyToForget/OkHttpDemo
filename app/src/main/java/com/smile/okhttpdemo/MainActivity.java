package com.smile.okhttpdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smile.okhttpintegration.OkCallback;
import com.smile.okhttpintegration.OkHttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String HOME = "http://api.tuikexing.com/api/anon/index";
    private final static String LOGIN = "http://api.tuikexing.com/service/oauth/login";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        OkHttp.setConnectTimeOut(30);
        OkHttp.setReadTimeOut(30);
        OkHttp.setWriteTimeOut(30);
        OkHttp.setUploadReadTimeOut(30);
        OkHttp.setUploadWriteTimeOut(30);
        //OkHttp.setRetryCount(4);
        //OkHttp.setErrorCode("setErrorCode");
        OkHttp.setErrorStatus("state", "0000");
        //OkHttp.setUserAgent("setUserAgent");

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> params = new HashMap<>();
                params.put("lang", "zh");
                params.put("type", 2);
                params.put("page", 1);
                params.put("pageSize", 10);
                OkHttp.get(HOME, params, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("email", "smile");
                params.put("password", "smile");
                OkHttp.post(LOGIN, params, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_post_timeout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("email", "smile");
                params.put("password", "smile");
                OkHttp.post(10, LOGIN, params, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_post_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setAge(10);
                user.setName("Smile");
                user.setSex("男");
                String json = new Gson().toJson(user);

                OkHttp.postJson(LOGIN, json, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("email", "smile");
                params.put("password", "smile");
                OkHttp.upload(LOGIN, params, "file[]", "file01", new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_upload_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("email", "smile");
                params.put("password", "smile");
                List<String> list = new ArrayList<>();
                list.add("file01");
                list.add("file02");
                list.add("file03");
                OkHttp.uploadMulti(LOGIN, params, "file[]", list, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
