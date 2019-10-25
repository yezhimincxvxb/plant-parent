package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_seed_exchange")
@Data
public class SeedExchange implements Serializable {

    private static final long serialVersionUID = 6849549158201854212L;

    @TableId
    private Integer id;

    /**
     * 种子id
     */
    @JSONField(ordinal = 1)
    @TableField
    private Integer seedId;

    /**
     * 菌包名称
     */
    @JSONField(ordinal = 2)
    @TableField(exist = false)
    private String seedName;


    /**
     * 种植用户id
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer userId;

    /**
     * 用户名
     */
    @JSONField(ordinal = 4)
    @TableField(exist = false)
    private String userName;

    /**
     * 兑换份数
     */
    @JSONField(ordinal = 5)
    @TableField
    private Integer exchangeCount;

    /**
     * 快递记录id
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer expressId;

    /**
     * [0未收货，1已收货]
     */
    @TableField
    private Integer state;

}