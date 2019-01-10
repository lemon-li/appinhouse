package com.seasungames.appinhouse.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.Json;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseVo<T> {

    private T data;

    private String message;

    private int code;

    public ResponseVo setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseVo setCode(int code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String toJson() {
        return Json.encode(this);
    }
}
