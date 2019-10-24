package com.moguying.plant.core.entity.mall;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.entity.system.PayOrder;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("plant_mall_order")
public class MallOrder implements Serializable, PayOrder {

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单流水号
     */
    @JSONField(ordinal = 2)
    @TableField
    private String orderNumber;

    /**
     * 用户id
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer userId;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 4 ,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal buyAmount;

    /**
     * 购买总蘑菇币
     */
    @JSONField(ordinal = 26)
    @TableField
    private Integer totalCoins;

    /**
     * 买家备注
     */
    @JSONField(ordinal = 5)
    @TableField
    private String buyMark;

    /**
     * 订单快递费
     */
    @JSONField(ordinal = 6,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal feeAmount;

    /**
     * 订单状态[0未支付，1已支付（待发货），2已发货（待收货），3已完成，4已关单,5已取消]
     */
    @JSONField(ordinal = 7)
    @TableField
    private Integer state;

    /**
     * 用户收货地址id
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer addressId;

    /**
     * 下单时间
     */
    @JSONField(ordinal = 9,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 支付时间
     */
    @JSONField(ordinal = 10,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date payTime;

    /**
     * 关单时间
     */
    @JSONField(ordinal = 11,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date closeTime;

    /**
     * 发货时间
     */
    @JSONField(ordinal = 12,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date sendTime;

    /**
     * 是否已提醒过发货
     */
    @JSONField(ordinal = 13)
    @TableField
    private Boolean isNotice;

    /**
     * 确认收货时间
     */
    @JSONField(ordinal = 14,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date confirmTime;

    /**
     * 快递单号
     */
    @JSONField(ordinal = 15)
    @TableField
    private String expressOrderNumber;

    /**
     * 快递公司编码
     */
    @JSONField(ordinal = 16)
    @TableField
    private String expressComCode;

    /**
     * 取消订单原因
     */
    @JSONField(ordinal = 26)
    @TableField
    private String cancelReason;

    /**
     * 支付短信流水号
     */
    @JSONField(ordinal = 17)
    @TableField
    private String seqNo;

    /**
     * 卡支付金额
     */
    @JSONField(ordinal = 18,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal carPayAmount;

    /**
     * 余额支付金额
     */
    @JSONField(ordinal = 19,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal accountPayAmount;


    /**
     * 优惠金额
     */
    @JSONField(ordinal = 25,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal reducePayAmount;

    /**
     * 后台辅助字段
     * 用户手机号
     */
    @JSONField(ordinal = 20)
    @TableField(exist = false)
    private String phone;

    @JSONField(ordinal = 21,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date noticeTime;

    /**
     * 后台辅助字段
     * 用户真实姓名
     */
    @JSONField(ordinal = 22)
    @TableField(exist = false)
    private String realName;

    /**
     * 后台辅助字段
     * 订单地址详情
     */
    @JSONField(ordinal = 23)
    @TableField(exist = false)
    private UserAddress address;

    /**
     * 后台辅助字段
     * 订单商品详情
     */
    @JSONField(ordinal = 24)
    @TableField(exist = false)
    private List<OrderItem> details;

    @Override
    public MoneyOpEnum getOpType() {
        return MoneyOpEnum.BUY_MALL_PRODUCT;
    }

}