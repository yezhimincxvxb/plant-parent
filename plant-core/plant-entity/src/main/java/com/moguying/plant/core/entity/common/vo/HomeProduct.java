package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HomeProduct implements Serializable {

    private static final long serialVersionUID = 8877900538458503113L;

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String thumbPicUrl;

    @JSONField(ordinal = 3)
    private String name;

    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal price;

    @JSONField(ordinal = 5,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal oldPrice;

    @JSONField(ordinal = 6)
    private Integer hasCount;

    @JSONField(serialize = false)
    private Integer page = 1;

    @JSONField(serialize = false)
    private Integer size = 10;

    @JSONField(ordinal = 7)
    private Integer consumeCoins;

    @JSONField(serialize = false)
    private Boolean isHot;

    @JSONField(serialize = false)
    private Integer typeId;
}
