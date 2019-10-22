package com.moguying.plant.core.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.common.util.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_reap
 * @author 
 */
@Data
public class Reap implements Serializable {

    private static final long serialVersionUID = -2497852734764046810L;

    @Excel(name = "序号")
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 种植订单id
     */
    @JSONField(ordinal = 2)
    private Integer orderId;

    @Excel(name = "种植流水号")
    @JSONField(ordinal = 3)
    private String orderNumber;

    /**
     * 种子id
     */
    @JSONField(ordinal = 4)
    private Integer seedType;

    @JSONField(ordinal = 5,deserialize = false)
    private String seedName;

    @Excel(name = "菌包名称")
    @JSONField(ordinal = 6)
    private String seedTypeName;

    @Excel(name = "种植期限(天)")
    @JSONField(ordinal = 7)
    private Integer seedGrowDays;

    @JSONField(ordinal = 8,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal seedPrice;


    /**
     * 用户id
     */
    @JSONField(ordinal = 9)
    private Integer userId;

    @Excel(name = "用户名")
    @JSONField(ordinal = 10)
    private String phone;

    /**
     * 棚区id
     */

    @JSONField(ordinal = 11)
    private Integer blockId;

    @Excel(name = "种植棚区")
    @JSONField(ordinal = 12)
    private String blockNumber;

    /**
     * 种植份数
     */
    @Excel(name = "种植份数")
    @JSONField(ordinal = 13)
    private Integer plantCount;

    /**
     * 种植名称
     */
    @Excel(name = "种植名称")
    @JSONField(ordinal = 26)
    private String plantName;

    /**
     * 种植时间
     */
    @Excel(name = "种植时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 14,format = "yyyy-MM-dd HH:mm:ss")
    private Date plantTime;

    /**
     * 预计收益
     */
    @Excel(name = "预计收益")
    @JSONField(ordinal = 15,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal preProfit;

    /**
     * 预计收回种本成本
     */
    @Excel(name = "种植金额")
    @JSONField(ordinal = 16,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal preAmount;

    /**
     * 最后一次收获时间
     */
    @Excel(name = "预期采摘时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 17,format = "yyyy-MM-dd HH:mm:ss")
    private Date preReapTime;

    /**
     * 实际收益
     */
    @JSONField(ordinal = 18,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal recProfit;

    /**
     * 实际收回成本
     */
    @JSONField(ordinal = 19,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal recAmount;

    /**
     * 实际最后一次收获时间
     */
    @JSONField(ordinal = 20,format = "yyyy-MM-dd HH:mm:ss")
    private Date recReapTime;

    /**
     * 出售时间
     */
    @Excel(name = "出售时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 21,format = "yyyy-MM-dd HH:mm:ss")
    private Date saleTime;

    /**
     * 收获次数
     */
    @JSONField(ordinal = 22)
    private Integer reapTimes;

    /**
     * 状态：[0待采摘，1已采摘，2售卖中，3已售卖，4已兑换]
     */
    @Excel(name = "状态",replace = {"待采摘_0","已采摘_1","售卖中_2","已售卖_3","已兑换_4"})
    @JSONField(ordinal = 23)
    private Integer state;

    /**
     * 添加时间
     */
    @Excel(name = "操作时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 24,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;


    /**
     * 辅助字段
     * 背景图
     */
    @JSONField(ordinal = 25)
    private String picUrl;

    /**
     * 兑换时间
     */
    @Excel(name = "兑换时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 26, format = "yyyy-MM-dd HH:mm:ss")
    private Date exchangeTime;

    /**
     * 用户真实姓名
     */
    @JSONField(ordinal = 26)
    private String realName;

}