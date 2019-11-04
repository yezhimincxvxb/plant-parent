package com.moguying.plant.core.entity.bargain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价订单
 */
@Data
@TableName("plant_bargain_detail")
public class BargainDetail implements Serializable {

    private static final long serialVersionUID = 6528830212742119899L;

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 分享者id
     */
    @TableField
    @JSONField(ordinal = 2)
    private Integer userId;

    /**
     * 产品id
     */
    @TableField
    @JSONField(ordinal = 3)
    private Integer productId;

    /**
     * 产品数量
     */
    @TableField
    @JSONField(ordinal = 4)
    private Integer productCount;

    /**
     * 总价
     */
    @TableField
    @JSONField(ordinal = 5, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    /**
     * 已砍价格
     */
    @TableField
    @JSONField(ordinal = 6, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal bargainAmount;

    /**
     * 剩余价格
     */
    @TableField
    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal leftAmount;

    /**
     * 需砍总数
     */
    @TableField
    @JSONField(ordinal = 8)
    private Integer totalCount;

    /**
     * 已砍次数
     */
    @TableField
    @JSONField(ordinal = 9)
    private Integer bargainCount;

    /**
     * 是否关单
     */
    @TableField
    @JSONField(ordinal = 10)
    private Boolean state;

    /**
     * 添加时间
     */
    @TableField
    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 砍价时间
     */
    @TableField
    @JSONField(ordinal = 12, format = "yyyy-MM-dd HH:mm:ss")
    private Date bargainTime;

    /**
     * 关单时间
     */
    @TableField
    @JSONField(ordinal = 13, format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 订单号
     */
    @TableField
    @JSONField(ordinal = 14)
    private String orderNumber;


    /**
     * 辅助字段
     */
    @TableField(exist = false)
    private String message;

}
