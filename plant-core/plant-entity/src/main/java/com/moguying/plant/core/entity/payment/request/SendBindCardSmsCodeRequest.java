package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SendBindCardSmsCodeRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String bkMerNo;

    //个人银行卡
    @JSONField(ordinal = 2)
    private String cardType = "0";

    @JSONField(ordinal = 3)
    private String bankNo = "";

    @JSONField(ordinal = 4)
    private String cardNo;

    @JSONField(ordinal = 5)
    private String merMerOrderNo = "";

    @JSONField(ordinal = 6)
    private String phone;

    @JSONField(ordinal = 7)
    private String idNo;

    @JSONField(ordinal = 8)
    private String custName;
}
