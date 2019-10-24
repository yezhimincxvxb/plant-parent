package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {

    /**
     * 购物车中的id
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 商品id
     */
    @JSONField(ordinal = 2)
    private Integer productId;

    /**
     * 商品名
     */
    @JSONField(ordinal = 3)
    private String productName;

    /**
     * 商品简介
     */
    @JSONField(ordinal = 4)
    private String productSummary;


    /**
     * 商品单价
     */
    @JSONField(ordinal = 5,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productPrice;

    /**
     * 购买总份数
     */
    @JSONField(ordinal = 6)
    private Integer buyCount;

    /**
     * 快递费
     */
    @JSONField(ordinal = 7)
    private BigDecimal expressFee;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 8)
    private String thumbPicUrl;

    /**
     * 是否勾选
     */
    @JSONField(ordinal = 9)
    private Boolean isCheck;


    /**
     * 商品剩余库存
     */
    @JSONField(ordinal = 10)
    private Integer productLeftCount;

    /**
     * 商品蘑菇币
     */
    @JSONField(ordinal = 11)
    private Integer coin;


}
