package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HomeProductDetail implements Serializable {

    private static final long serialVersionUID = 6802770754527623075L;

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String picUrl;

    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal price;

    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal oldPrice;

    @JSONField(ordinal = 5)
    private Integer hasCount;

    @JSONField(ordinal = 6)
    private String name;

    @JSONField(ordinal = 7)
    private String summaryDesc;

    @JSONField(ordinal = 8)
    private String productDesc;


    @JSONField(ordinal = 9)
    private String thumbPicUrl;

    @JSONField(ordinal = 10)
    private String leftCount;

    @JSONField(ordinal = 11)
    private Integer consumeCoins;

}
