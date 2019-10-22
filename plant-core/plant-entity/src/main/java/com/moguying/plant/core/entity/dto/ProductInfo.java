package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情
 */
@Data
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -6281422935728238952L;

    /**
     * 商品数量
     */
    @JSONField(ordinal = 1)
    private Integer productCount;

    /**
     * 商品名称
     */
    @JSONField(ordinal = 2)
    private String productName;
}
