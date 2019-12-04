package com.moguying.plant.core.entity.mall;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("plant_mall_order_detail")
public class MallOrderDetail implements Serializable {

    private static final long serialVersionUID = 7971475355110963933L;


    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单流水号
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer orderId;

    /**
     * 用户id
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer userId;

    /**
     * 产品id
     */
    @JSONField(ordinal = 4)
    @TableField
    private Integer productId;

    /**
     * 购买数量
     */
    @JSONField(ordinal = 5)
    @Excel(name = "购买数量")
    @TableField
    private Integer buyCount;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 6)
    @Excel(name = "购买总价")
    @TableField
    private BigDecimal buyAmount;

    /**
     * 订单蘑菇币
     */
    @JSONField(ordinal = 7)
    @TableField
    private Integer buyCoins;


    @JSONField(ordinal = 8)
    @Excel(name = "订单流水号")
    @TableField(exist = false)
    private String orderNumber;

    @JSONField(ordinal = 9)
    @Excel(name = "用户姓名")
    @TableField(exist = false)
    private String realName;

    @JSONField(ordinal = 10)
    @Excel(name = "用户手机号")
    @TableField(exist = false)
    private String phone;

    @JSONField(ordinal = 11)
    @Excel(name = "产品名称")
    @TableField(exist = false)
    private String productName;


}