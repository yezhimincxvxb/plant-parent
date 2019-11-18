package com.moguying.plant.core.entity;


import com.moguying.plant.constant.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ResultData<T> {

    private MessageEnum messageEnum;

    private T data;
}
