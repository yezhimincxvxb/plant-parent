package com.moguying.plant.core.entity;


import com.moguying.plant.constant.MessageEnum;

public class ResultData<T> {

   private MessageEnum messageEnum;

   private T data;



    public ResultData(MessageEnum messageEnum, T data) {
        this.messageEnum = messageEnum;
        this.data = data;
    }

    public MessageEnum getMessageEnum() {
        return messageEnum;
    }

    public ResultData<T> setMessageEnum(MessageEnum messageEnum) {
        this.messageEnum = messageEnum;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultData<T> setData(T data) {
        this.data = data;
        return this;
    }
}
