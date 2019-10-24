package com.moguying.plant.core.entity.farmer.vo;

import lombok.Data;

@Data
public class EnergyItem {

    private Integer id;

    private Integer incrGrowUpCount;

    private Long leftSecond;

    private Long dismissSecond;

    private Integer pickState;
}
