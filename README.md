# OkhttpIntegration

对 OkHttp 的简单封装

目前对应 OkHttp 版本`3.8.1`.

## Demo
<img src="gif/demo01.gif" alt="demo"/>


## 用法

* Android Studio
	
	```
	compile 'com.smile:okhttpintegration:1.0.1'
	```
	
```java
public class MyApplication extends Application
{	
	@Override
    public void onCreate()
    {
        super.onCreate();

        OkHttp.setConnectTimeOut(5);
        OkHttp.setReadTimeOut(5);
        OkHttp.setWriteTimeOut(5);
        //OkHttp.setRetryCount(4);
        OkHttp.setErrorCode("setErrorCode");
        OkHttp.setUserAgent("setUserAgent");

    }
}
```

### GET

```java
 OkHttp.get("http://api.tuikexing.com", params, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                    }
                });
```

### POST

```java
 OkHttp.post("http://api.tuikexing.com", params, new OkCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("onFailure", error);
                    }
                });

```




