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


一般情况下，对接口请求响应结果的判断有两种情况：
- 返回的 json 数据中会包含诸如 error_code 的 key，用来表明此接口响应失败，如下代码所示；
```java
    {
        request: "http://api.tuikexing.com",
        error_code: 50001,
        error: "！",
        error_description: "{错误码0: 1502183653-2442}"
    }
```
- 返回的 json 数据结构有固定的诸如 status 的 key，根据 status key 所对应的 value 值来判断此接口是响应失败，
你可以指定当 status 为 200 时代表接口响应成功。如下所示：
```java
    {
        status: 200,
        msg: "success",
        data: [ ],
        totalPage: 0,
        totalCount: 0
    }
```


**注意** 任何时候对请求响应结果的判断不要依赖于http协议的CODE值，
而应该首先检查返回的json数据结构是否存在 `error_code` 字段或 `status` 的值。


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




