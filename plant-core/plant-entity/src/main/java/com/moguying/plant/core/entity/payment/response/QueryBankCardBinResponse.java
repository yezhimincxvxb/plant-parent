package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class QueryBankCardBinResponse implements PaymentResponseInterface {
    private String bankCode;

    private String bankName;

    private String bankNumber;

    private String unionNumbers;

    private String dfSwitch;

    private String csSwitch;

    private String b2bSwitch;

    private String xyFpSwitch;

    private String xyFpCreditSwitch;

    private String bingPerUnionNumbers;

    private String cardType;
}
