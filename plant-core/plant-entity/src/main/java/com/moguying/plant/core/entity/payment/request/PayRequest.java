package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PayRequest implements PaymentRequestInterface {

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

    @JSONField(ordinal = 7)
    private String orderSubject;

    @JSONField(ordinal = 8)
    private String orderBody = "";

    @JSONField(ordinal = 9)
    private String apiPayType;

    @JSONField(ordinal = 10)
    private String tempRoutingList = "";

    @JSONField(ordinal = 11)
    private String ptUndertakeRate = "";

    @JSONField(ordinal = 12)
    private String sellerNo;

    @JSONField(ordinal = 13)
    private String smsCode;

    @JSONField(ordinal = 14)
    private String seqNo;
}
