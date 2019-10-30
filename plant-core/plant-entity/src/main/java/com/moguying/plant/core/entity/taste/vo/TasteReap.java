package com.moguying.plant.core.entity.taste.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TasteReap {
    @JSONField(ordinal = 1)
    private String seedName;

    @JSONField(ordinal = 2)
    private Integer growDays;

    @JSONField(ordinal = 3)
    private Integer plantCount;

    @JSONField(ordinal = 4)
    private BigDecimal plantWeigh;

    @JSONField(ordinal = 5)
    private Date reapTime;
}
