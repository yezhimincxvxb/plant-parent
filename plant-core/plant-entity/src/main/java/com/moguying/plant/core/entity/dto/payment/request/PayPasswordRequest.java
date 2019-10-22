package com.moguying.plant.core.entity.dto.payment.request;

public class PayPasswordRequest implements PaymentRequestInterface{

    private String merchantNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
