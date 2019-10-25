package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PaySignQueryRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String cardNo;

    @JSONField(ordinal = 2)
    private String custName;

    @JSONField(ordinal = 3)
    private String phone;

    @JSONField(ordinal = 4)
    private String idNo;

    @JSONField(ordinal = 5)
    private String payAmount;

    @JSONField(ordinal = 6)
    private String merOrderNo;
}
