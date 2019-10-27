package com.moguying.plant.core.entity;

import lombok.Data;

@Data
public class PageSearch<T> {
    private Integer page = 1;
    private Integer size = 10;
    private T where;
}
