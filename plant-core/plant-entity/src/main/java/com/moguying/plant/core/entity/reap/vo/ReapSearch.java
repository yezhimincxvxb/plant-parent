package com.moguying.plant.core.entity.reap.vo;

import lombok.Data;

@Data
public class ReapSearch {
    private Integer page = 1;

    private Integer size = 10;

    private Integer type;

}
