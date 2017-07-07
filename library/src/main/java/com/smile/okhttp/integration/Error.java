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
package com.smile.okhttp.integration;

/**
 * 服务器端返回错误信息对象
 *
 * @author Smile Wei
 * @since 2017.04.11
 */
public class Error {

    /**
     * 错误码
     */
    private int error_code;
    /**
     * 错误信息简单说明
     */
    private String error;

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

}
