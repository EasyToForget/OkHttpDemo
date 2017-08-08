# [OkhttpIntegration](https://github.com/EasyToForget/OkHttpDemo)

对 OkHttp 的简单封装

目前对应 [OkHttp](https://github.com/square/okhttp) 版本`3.8.1`.

## Demo
<img src="gif/demo01.gif" alt="demo"/>


## 使用

Android Studio
	
``` java
	compile 'com.smile:okhttpintegration:1.0.4'
```
	
## 初始化
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
- 返回的 json 数据结构有固定的诸如 status 的 key，根据 status key 所对应的 value 值来判断此接口是响应失败，你可以指定当 status 为 200 时代表接口响应成功。如下所示：
```java
    {
        status: 200,
        msg: "success",
        data: [ ],
        totalPage: 0,
        totalCount: 0
    }
```


**注意** 任何时候对请求响应结果的判断不要依赖于 http 协议的 CODE 值，而应该首先检查返回的 json 数据结构是否存在 `error_code` 字段或 `status` 的值。


### GET

```java
   Map<String, Object> params = new HashMap<>();
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
```

### POST

```java
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

```


### POST JSON

```java
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

```

### UPLOAD FILE

```java
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

```

### UPLOAD MULTIPLE FILE

```java
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

```



### License

```
OkHttpDemo for Android
Copyright (c) 2017 Smile Wei (http://github.com/EasyToForget).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




