package com.moguying.plant.core.entity.seed;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_seed_order
 * @author 
 */
@Data
public class SeedOrder implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 菌包类型
     */
    @JSONField(ordinal = 2)
    private Integer seedType;

    /**
     * 菌包类型名称
     */
    @Excel(name = "菌包类型")
    @JSONField(ordinal = 3)
    private String seedTypeName;

    /**
     * 用户id
     */
    @JSONField(ordinal = 4)
    private Integer userId;


    /**
     * 用户名
     */
    @Excel(name = "用户名")
    @JSONField(ordinal = 5)
    private String phone;


    /**
     * 购买总份数
     */
    @Excel(name = "购买总份数")
    @JSONField(ordinal = 6)
    private Integer buyCount;

    /**
     * 购买总价
     */
    @Excel(name = "购买总价")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    private BigDecimal buyAmount;

    /**
     * 种植份数
     */
    @Excel(name = "种植份数")
    @JSONField(ordinal = 8)
    private Integer plantCount;


    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    @JSONField(ordinal = 9)
    private String realName;
}