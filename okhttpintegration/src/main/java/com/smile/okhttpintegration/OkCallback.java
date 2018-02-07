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

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Smile Wei
 * @since 2016/8/2.
 */
public abstract class OkCallback implements Callback {
    private static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(Call call, final IOException e) {
        //请求连接超时或请求响应失败
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null) {
            //请求响应为空
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure("");
                }
            });
            return;
        }
        final String res = body.string();

        if (OkHttp.isReturn) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onResponse(res);
                }
            });
            return;
        }

        //判断当前请求响应结果是不是json字符串
        if (!JsonUtil.isGoodJson(res)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(res);
                }
            });
            return;
        }

        if (JsonUtil.isErrorCode(res)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(res);
                }
            });
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(res);
            }
        });
    }

    public abstract void onResponse(String response);

    public abstract void onFailure(String error);

}
