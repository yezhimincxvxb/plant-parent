package com.moguying.plant.core.entity.coin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-蘑菇币日志类
 */
@TableName("plant_sale_coin_log")
@Data
public class SaleCoinLog implements Serializable {

    private static final long serialVersionUID = -4454040989808070649L;

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField
    private Integer userId;

    /**
     * 蘑菇币
     */
    @TableField
    private Integer affectCoin;

    /**
     * 类型
     */
    @TableField
    private Integer affectType;

    /**
     * ID集合
     */
    @TableField
    private String affectDetailId;
}
