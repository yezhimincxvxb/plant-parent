package com.moguying.plant.core.entity.activity;

import lombok.Data;

@Data
public class LotteryRule {
    private String id;

    private Integer minPlantCount;

    private Integer maxPlantCount;

    private String rule;

}
