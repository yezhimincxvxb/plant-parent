package com.moguying.plant.core.entity;

public class TriggerEventResult<T> {
    private T data;
    private Integer userId;

    public T getData() {
        return data;
    }

    public TriggerEventResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public TriggerEventResult<T> setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}
