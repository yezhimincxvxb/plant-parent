package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class RealnameRequest implements PaymentRequestInterface {


    @JSONField(ordinal = 1)
    private String type = "per";

    @JSONField(ordinal = 2)
    private String sonMerNo = "";

    @JSONField(ordinal = 3)
    private String legalPersonName = "";

    @JSONField(ordinal = 4)
    private String legalPersonPhone = "";

    @JSONField(ordinal = 5)
    private String typeOfID = "0";

    @JSONField(ordinal = 6)
    private String legalPersonIdnum = "";

    @JSONField(ordinal = 7)
    private String longTimeOrNoPer = "";

    @JSONField(name = "IDValidity", ordinal = 8)
    private String IDValidity = "";

    @JSONField(ordinal = 9)
    private String legalPersonIdphotoa;

    @JSONField(ordinal = 10)
    private String legalPersonIdphotob;

    @JSONField(ordinal = 11)
    private String merchantName = "";

}
