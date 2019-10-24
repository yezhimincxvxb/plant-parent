package com.moguying.plant.core.entity.payment.request;


/**
 * 快捷注册发送短信
 */
public class SendRegSmsCodeRequest implements PaymentRequestInterface{

    private String merType;

    private String custName;

    private String idNo;

    private String phone;

    private String merchantName = "";

    private String customerNo;


    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
