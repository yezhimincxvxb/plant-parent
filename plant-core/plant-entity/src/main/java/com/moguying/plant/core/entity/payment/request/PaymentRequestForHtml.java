package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PaymentRequestForHtml {

    @JSONField(ordinal = 1)
    private String merNo;

    @JSONField(ordinal = 2)
    private String version;

    @JSONField(ordinal = 3)
    private String notifyUrl;

    @JSONField(ordinal = 4)
    private String timestamp;

    @JSONField(ordinal = 5)
    private String apiContent;

    @JSONField(ordinal = 6)
    private String signType;

    @JSONField(ordinal = 7)
    private String sign;

    @JSONField(ordinal = 8)
    private String actionUrl;


}
