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
    @JSONField(ordinal = 13)
    @TableField
    private Integer orderId;

    /**
     * 用户id
     */
    @JSONField(ordinal = 12)
    @TableField
    private Integer userId;

    /**
     * 产品id
     */
    @JSONField(ordinal = 11)
    @TableField
    private Integer productId;

    /**
     * 购买数量
     */
    @JSONField(ordinal = 6)
    @Excel(name = "购买数量",orderNum = "5")
    @TableField
    private Integer buyCount;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 7,serializeUsing = BigDecimalSerialize.class)
    @Excel(name = "购买总价",orderNum = "6")
    @TableField
    private BigDecimal buyAmount;

    /**
     * 订单蘑菇币
     */
    @JSONField(ordinal = 10)
    @TableField
    private Integer buyCoins;


    @JSONField(ordinal = 2)
    @Excel(name = "订单流水号",orderNum = "1")
    @TableField(exist = false)
    private String orderNumber;

    @JSONField(ordinal = 9, format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间",format = "yyyy-MM-dd HH:mm:ss",orderNum = "8")
    @TableField(exist = false)
    private String addTime;

    @JSONField(ordinal = 8)
    @Excel(name = "状态", replace = {"未支付_0", "已支付_1", "已发贷_2", "已完成_3", "已关单_4", "已取消_5"},orderNum = "7")
    @TableField(exist = false)
    private String state;

    @JSONField(ordinal = 4)
    @Excel(name = "用户姓名",orderNum = "3")
    @TableField(exist = false)
    private String realName;

    @JSONField(ordinal = 5)
    @Excel(name = "用户手机号",orderNum = "4")
    @TableField(exist = false)
    private String phone;

    @JSONField(ordinal = 3)
    @Excel(name = "产品名称",orderNum = "2")
    @TableField(exist = false)
    private String productName;


}