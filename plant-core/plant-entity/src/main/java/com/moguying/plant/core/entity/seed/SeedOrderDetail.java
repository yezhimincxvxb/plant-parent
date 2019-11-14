package com.moguying.plant.core.entity.seed;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.entity.system.PayOrder;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("plant_seed_order_detail")
@Data
public class SeedOrderDetail implements Serializable, PayOrder {

    private static final long serialVersionUID = 7865512196769013283L;


    @JSONField(ordinal = 1)
    @Excel(name = "序号")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    @JSONField(ordinal = 2)
    @Excel(name = "流水号")
    @TableField
    private String orderNumber;

    /**
     * 种子id
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer seedId;

    @JSONField(ordinal = 4)
    @TableField(exist = false)
    private String seedName;

    @JSONField(ordinal = 5)
    @Excel(name = "菌包类型名称")
    @TableField(exist = false)
    private String seedTypeName;

    @JSONField(ordinal = 6)
    @Excel(name = "生长周期")
    @TableField(exist = false)
    private Integer seedGrowDays;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    @TableField(exist = false)
    private BigDecimal seedPrice;

    /**
     * 用户id
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer userId;

    /**
     * 购买份数
     */
    @JSONField(ordinal = 9)
    @Excel(name = "购买份数")
    @TableField
    private Integer buyCount;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 10,serializeUsing = BigDecimalSerialize.class)
    @Excel(name = "购买总价")
    @TableField
    private BigDecimal buyAmount;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 11)
    @Excel(name = "添加时间",format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 支付时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 12)
    @Excel(name = "支付时间",format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date payTime;

    /**
     * 支付短信流水号
     */
    @JSONField(ordinal = 13)
    @TableField
    private String seqNo;

    /**
     * 余额支付金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 14)
    @Excel(name = "余额支付")
    @TableField
    private BigDecimal accountPayAmount;

    /**
     * 卡支付金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 15)
    @Excel(name = "卡支付")
    @TableField
    private BigDecimal carPayAmount;


    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 16)
    @Excel(name = "关单时间",format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date closeTime;


    @JSONField(ordinal = 17)
    @Excel(name = "状态",replace = {"待支付_0","已支付_1","已取消_2"})
    @TableField
    private Integer state;

    /**
     * 辅助字段，菌包图片
     */
    @JSONField(ordinal = 18)
    @TableField(exist = false)
    private String picUrl;

    /**
     * 辅助字段,用户手机号
     */
    @JSONField(ordinal = 19)
    @Excel(name = "手机号")
    @TableField(exist = false)
    private String phone;

    /**
     * 优惠金额
     */
    @JSONField(ordinal = 20,serializeUsing = BigDecimalSerialize.class)
    @Excel(name = "优惠金额")
    @TableField
    private BigDecimal reducePayAmount;

    @JSONField(ordinal = 21,serialize = false)
    @TableField(exist = false)
    private Integer seedTypeId;

    /**
     * 实际支付金额
     */
    @JSONField(ordinal = 22,deserialize = false,serializeUsing = BigDecimalSerialize.class)
    @TableField(exist = false)
    private BigDecimal realPayAmount;

    @JSONField(ordinal = 23)
    @Excel(name = "姓名")
    @TableField(exist = false)
    private String realName;

    /**
     * 查询辅助时间
     */
    @TableField(exist = false)
    private Date startTime;

    /**
     * 查询辅助时间
     */
    @TableField(exist = false)
    private Date endTime;

    @TableField(exist = false)
    private Boolean isForNew;

    @Override
    public MoneyOpEnum getOpType() {
        return MoneyOpEnum.BUY_SEED_ORDER;
    }

    @Override
    public BigDecimal getFeeAmount() {
        return BigDecimal.ZERO;
    }

}