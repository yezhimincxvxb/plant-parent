package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.dto.UserAddress;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBuyResponse {

    /**
     * 地址
     */
    @JSONField(ordinal = 1)
    private UserAddress address;

    /**
     * 订单项
     */
    @JSONField(ordinal = 2)
    private List<OrderItem> orderItems;

    /**
     * 商品总价
     */
    @JSONField(ordinal = 3,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productAmount;

    /**
     * 运费
     */
    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal expressFee;

    /**
     * 订单总价
     */
    @JSONField(ordinal = 5,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    /**
     * 商品数量
     */
    @JSONField(ordinal = 6)
    private Integer itemCount;

    /**
     * 订单消耗蘑菇币
     */
    @JSONField(ordinal = 7)
    private Integer totalCoins;

}
