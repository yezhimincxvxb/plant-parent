package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_mall_product
 * @author 
 */
@Data
public class MallProduct implements Serializable {

    private static final long serialVersionUID = -1145586995248505240L;

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 图片地址
     */
    @JSONField(ordinal = 2)
    private String picUrl;

    /**
     * 缩略图地址
     */
    @JSONField(ordinal = 3)
    private String thumbPicUrl;

    /**
     * 产品名称
     */
    @JSONField(ordinal = 4)
    private String name;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    private BigDecimal price;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 6)
    private BigDecimal oldPrice;

    /**
     * 快递费
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    private BigDecimal fee;

    /**
     * 总库存
     */
    @JSONField(ordinal = 8)
    private Integer totalCount;

    /**
     * 库存
     */
    @JSONField(ordinal = 9)
    private Integer leftCount;

    /**
     * 购买份数
     */
    @JSONField(ordinal = 10)
    private Integer hasCount;

    /**
     * 商品摘要
     */
    @JSONField(ordinal = 11)
    private String summaryDesc;

    /**
     * 是否上架[0否，1是]
     */
    @JSONField(ordinal = 12)
    private Boolean isShow;

    /**
     * 配送信息
     */
    @JSONField(ordinal = 13)
    private String deliveryInfo;

    /**
     * 商品详情
     */
    @JSONField(ordinal = 14)
    private String productDesc;

    @JSONField(ordinal = 15,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 一份所需消耗的蘑菇币
     */
    @JSONField(ordinal = 16)
    private Integer consumeCoins;


}