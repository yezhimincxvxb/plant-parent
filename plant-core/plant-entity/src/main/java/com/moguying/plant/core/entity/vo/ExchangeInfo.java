package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 兑换信息
 */
@Data
public class ExchangeInfo implements Serializable {

    private static final long serialVersionUID = -3263899247080850948L;

    /**
     * 序号
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 兑换名称
     */
    @JSONField(ordinal = 2)
    private String name;

    /**
     * 兑换份数
     */
    @JSONField(ordinal = 3)
    private Integer number;

    /**
     * 券面额
     */
    @JSONField(ordinal = 4)
    private Double amount;

    /**
     * 券类型
     */
    @JSONField(ordinal = 5)
    private Integer type;

    /**
     * 蘑菇币数量
     */
    @JSONField(ordinal = 6)
    private Integer count;

    /**
     * 兑换时间
     */
    @JSONField(ordinal = 7)
    private String time;

    /**
     * 单价
     */
    @JSONField(ordinal = 8, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal price;

    /**
     * 是否勾选，默认不勾选
     */
    @JSONField(ordinal = 9)
    private Boolean isCheck = false;

    /**
     * 图片地址
     */
    @JSONField(ordinal = 10)
    private String picUrl;

    /**
     * 满多少才能减
     */
    @JSONField(ordinal = 11, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMin;

    /**
     * 使用与什么菌包
     */
    @JSONField(ordinal = 12)
    private String seedName;

    public String getSeedName() {
        return seedName == null ? "适用于所有商城菌包" : "适用于" + seedName;
    }
}
