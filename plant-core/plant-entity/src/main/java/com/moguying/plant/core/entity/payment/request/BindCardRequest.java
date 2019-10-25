package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BindCardRequest implements PaymentRequestInterface{

    @JSONField(ordinal = 1)
    private String seqNo;

    @JSONField(ordinal = 2)
    private String bkMerNo;

    @JSONField(ordinal = 3)
    private String cardNo;

    @JSONField(ordinal = 4)
    private String account = "";

    @JSONField(ordinal = 5)
    private String smsCode;

}
