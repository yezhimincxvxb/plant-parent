package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("plant_seed_days")
@Data
public class SeedDays implements Serializable {
    private static final long serialVersionUID = 7035737061139683760L;
    /**
     * 生长天数
     */
    @JSONField(name = "days")
    @TableId
    private Integer growDays;

    /**
     * 生长周期名称
     */
    @JSONField(name = "name")
    @TableField
    private String growDaysName;

    /**
     * 备注
     */
    @TableField
    private String mark;


    /**
     * 首次种植运营结算比
     */
    @TableField
    private BigDecimal firstPlantRate;

    /**
     * 正常种植运营结算比
     */
    @TableField
    private BigDecimal plantRate;

}