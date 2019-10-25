package com.moguying.plant.core.entity.seed;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("plant_seed_inner_order")
@Data
public class SeedInnerOrder implements Serializable {

    private static final long serialVersionUID = 6065920189507676872L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    @TableField
    private String orderNumber;

    /**
     * 种子id
     */
    @TableField
    private Integer seedId;

    /**
     * 用户id
     */
    @TableField
    private Integer userId;

    /**
     * 棚区id
     */
    @TableField
    private Integer blockId;

    /**
     * 购买种子份数
     */
    @TableField
    private Integer plantCount;

    /**
     * 购买总价
     */
    @TableField
    private BigDecimal plantAmount;

    /**
     * 种植利润
     */
    @TableField
    private BigDecimal plantProfit;

    /**
     * 种子订单状态[0已购买未种植，1已种植,2已取消]
     */
    @TableField
    private Boolean plantState;

    /**
     * 种植时间
     */
    @TableField
    private Date plantTime;

    /**
     * 结束时间
     */
    @TableField
    private Date endTime;
}