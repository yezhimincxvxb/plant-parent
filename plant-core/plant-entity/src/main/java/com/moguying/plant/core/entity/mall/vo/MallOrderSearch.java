package com.moguying.plant.core.entity.mall.vo;

import lombok.Data;

@Data
public class MallOrderSearch {
    private String phone;

    private Integer page = 1;

    private Integer size = 10;

    private String orderNumber;

    private Integer state;
}
