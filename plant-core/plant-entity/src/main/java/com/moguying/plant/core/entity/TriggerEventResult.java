package com.moguying.plant.core.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TriggerEventResult<T> {
    private T data;
    private Integer userId;
}
