# OkhttpIntegration

对 OkHttp 的简单封装

目前对应 OkHttp 版本`3.8.1`.

## Demo
<img src="gif/demo01.gif" alt="demo"/>


## 用法

* Android Studio
	
	```
	compile 'com.smile:okhttpintegration:1.0.4'
	```
	
## 在 Application 中进行初始化
```java
    public class MyApplication extends Application{	
        @Override
        public void onCreate(){
            super.onCreate();

            OkHttp.setConnectTimeOut(30);
            OkHttp.setReadTimeOut(30);
            OkHttp.setWriteTimeOut(30);
            OkHttp.setUploadReadTimeOut(30);
            OkHttp.setUploadWriteTimeOut(30);
            OkHttp.setRetryCount(2);
            OkHttp.setErrorCode("setErrorCode");
            OkHttp.setErrorStatus("state", "0000");
            OkHttp.setUserAgent("setUserAgent");
        }
    }
```


|方法                        |    描述                  |
|:--------                  | :--------                 |
|setConnectTimeOut(30)      |与服务器建立连接超时时间    |
|setReadTimeOut(30)         |设置读取响应超时时间        |
|setWriteTimeOut(30)        |设置写入响应超时时间        |
|setUploadReadTimeOut(30)   |设置上传文件读取响应超时时间 |
|setUploadWriteTimeOut(30)  |设置上传文件写入响应超时时间 |
|setRetryCount(2)           |设置请求失败时的重试次数      |
|setUserAgent("")           |设置UserAgent                |

>setErrorCode("") 和 setErrorStatus("", "")


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




