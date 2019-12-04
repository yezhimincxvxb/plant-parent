package com.moguying.plant.core.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("plant_mall_order_detail")
public class MallOrderDetail implements Serializable {

    private static final long serialVersionUID = 7971475355110963933L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单流水号
     */
    @TableField
    private Integer orderId;

    /**
     * 用户id
     */
    @TableField
    private Integer userId;

    /**
     * 产品id
     */
    @TableField
    private Integer productId;

    /**
     * 购买数量
     */
    @TableField
    private Integer buyCount;

    /**
     * 购买总价
     */
    @TableField
    private BigDecimal buyAmount;

    /**
     * 订单蘑菇币
     */
    @TableField
    private Integer buyCoins;


}