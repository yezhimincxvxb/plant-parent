package com.moguying.plant.core.entity.dto.payment.request;

public class QueryBankCardBinRequest implements PaymentRequestInterface {
    private String type = "0";

    private String cardNo;

    public String getType() {
        return type;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
