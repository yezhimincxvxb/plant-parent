package com.moguying.plant.core.entity.dto.payment.request;

public class ModifyPayPasswordRequest implements PaymentRequestInterface {

    private String merchantNo;

    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
