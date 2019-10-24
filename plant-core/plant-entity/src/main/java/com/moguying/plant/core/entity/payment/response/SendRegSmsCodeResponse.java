package com.moguying.plant.core.entity.payment.response;

public class SendRegSmsCodeResponse implements PaymentResponseInterface{

    private String customerNo;

    private String serialNo;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
