package com.moguying.plant.core.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;


/**
 * 公共返回信息实体
 *
 * @param <T>
 */
public class ResponseData<T> implements Serializable {

    @JSONField(name = "msg", ordinal = 1)
    private String message;

    @JSONField(ordinal = 2)
    private Integer state;

    @JSONField(ordinal = 3)
    private T data;

    @JSONField
    private String token;

    public ResponseData() {
    }

    public ResponseData(String message, Integer state) {
        this(message, state, null);
    }

    public ResponseData(String message, Integer state, T data) {
        this.message = message;
        this.state = state;
        this.data = data;
    }

    public ResponseData(String message, Integer state, T data, String token) {
        this.message = message;
        this.state = state;
        this.data = data;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public ResponseData<T> setState(Integer state) {
        this.state = state;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseData<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ResponseData<T> setToken(String token) {
        this.token = token;
        return this;
    }
}
