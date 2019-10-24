package com.moguying.plant.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MessageEnum;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageResult</p>
 * <p>Description: </p>
 *
 * @author Qinhir
 * @date 2018-11-05 17:13
 */
public class PageResult<T> implements Serializable {
    @JSONField(name = "state",ordinal = 1)
    private Integer status = null;
    @JSONField(name = "msg",ordinal = 2)
    private String message = null;

    @JSONField(name = "count",ordinal = 3)
    private Integer count;

    @JSONField(name = "data",ordinal = 4)
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Integer status, String message, Integer count, List<T> data) {
        this.status = status;
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public PageResult(Integer count, List<T> data) {
        this.count = count;
        this.data = data;
        this.message = MessageEnum.SUCCESS.getMessage();
        this.status = MessageEnum.SUCCESS.getState();
    }

    public Integer getStatus() {
        return status;
    }

    public PageResult<T> setStatus(Integer status) {
        this.status = status;
        return  this;
    }

    public String getMessage() {
        return message;
    }

    public PageResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public PageResult<T> setCount(Integer count) {
        this.count = count;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public PageResult<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
