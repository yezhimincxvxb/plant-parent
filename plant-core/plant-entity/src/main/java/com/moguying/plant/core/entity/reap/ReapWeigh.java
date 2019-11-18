package com.moguying.plant.core.entity.reap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("plant_reap_weigh")
@RequiredArgsConstructor
@Accessors(chain = true)
public class ReapWeigh {

    @NonNull
    @TableId
    private Integer userId;

    /**
     * 总产量
     */
    @TableField
    private BigDecimal totalWeigh;


    /**
     * 已兑换产量
     */
    @TableField
    private BigDecimal hasExWeigh;


    /**
     * 可兑换产量
     */
    @TableField
    private BigDecimal availableWeigh;


    /**
     * 已领取收益
     */
    @TableField
    private BigDecimal hasProfit;


    /**
     * 可领取收益
     */
    @TableField
    private BigDecimal availableProfit;
}
