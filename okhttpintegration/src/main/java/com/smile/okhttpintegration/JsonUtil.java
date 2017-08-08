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

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author Smile Wei
 * @since 2016/11/2.
 */

public class JsonUtil {
    /**
     * 判断字符串是否为json字符串
     *
     * @param str 字符串
     * @return true or false
     */
    public static boolean isGoodJson(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        try {
            new JsonParser().parse(str);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 判断是否包含error code
     *
     * @param str 返回的字符串
     * @return true or false
     */
    public static boolean isErrorCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (str.startsWith("[") && str.endsWith("]")) {
            return false;
        }
        boolean isErrorCode = false;
        try {
            JSONObject jsonObject = new JSONObject(str);
            if (!TextUtils.isEmpty(OkHttp.statusKey)){
                String code = jsonObject.getString(OkHttp.statusKey);
                return !code.equals(OkHttp.statusValue);

            }
            Iterator<?> keyIterator = jsonObject.keys();
            String key;
            while (keyIterator.hasNext()) {
                key = (String) keyIterator.next();
                if (OkHttp.errorCode.equals(key)) {
                    isErrorCode = true;
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isErrorCode;
    }

}
