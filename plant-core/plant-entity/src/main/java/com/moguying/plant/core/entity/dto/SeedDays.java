package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_seed_days
 * @author 
 */
@Data
public class SeedDays implements Serializable {
    /**
     * 生长天数
     */
    @JSONField(name = "days")
    private Integer growDays;

    /**
     * 生长周期名称
     */
    @JSONField(name = "name")
    private String growDaysName;

    /**
     * 备注
     */
    private String mark;


    /**
     * 首次种植运营结算比
     */
    private BigDecimal firstPlantRate;

    /**
     * 正常种植运营结算比
     */
    private BigDecimal plantRate;

}