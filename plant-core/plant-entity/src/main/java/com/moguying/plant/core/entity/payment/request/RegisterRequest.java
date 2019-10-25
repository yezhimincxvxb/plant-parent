package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class RegisterRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String merType = "per";

    @JSONField(ordinal = 2)
    private String custName = "";

    @JSONField(ordinal = 3)
    private String idNo = "";

    @JSONField(ordinal = 4)
    private String phone;

    @JSONField(ordinal = 5)
    private String merchantName = "";

    @JSONField(ordinal = 6)
    private String customerNo;

    @JSONField(ordinal = 7)
    private String serialNo = "";

    @JSONField(ordinal = 8)
    private String smsCode = "";

    @JSONField(ordinal = 9)
    private String noSMS = "true";
}
