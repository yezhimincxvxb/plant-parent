package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserMallOrder {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JSONField(ordinal = 3)
    private Integer state;

    @JSONField(ordinal = 4)
    private String stateStr;

    @JSONField(ordinal = 5, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    @JSONField(ordinal = 6, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal buyAmount;

    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal feeAmount;

    @JSONField(ordinal = 11)
    private Integer totalCoins;

    @JSONField(ordinal = 8)
    private Integer productCount;

    @JSONField(ordinal = 9)
    private List<OrderItem> orderItems;

    @JSONField(ordinal = 10, name = "isNotice")
    private Boolean isNotice;

    @JSONField(ordinal = 11, serialize = false)
    private String orderNumber;

}
