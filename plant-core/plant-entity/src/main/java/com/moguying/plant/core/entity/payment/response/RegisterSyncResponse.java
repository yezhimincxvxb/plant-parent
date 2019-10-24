package com.moguying.plant.core.entity.payment.response;

public class RegisterSyncResponse implements PaymentResponseInterface{
    private String customerNo;

    private String merchantNo;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
