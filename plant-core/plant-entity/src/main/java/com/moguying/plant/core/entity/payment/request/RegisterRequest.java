package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class RegisterRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String merType = "per";

    @JSONField(ordinal = 2)
    private String custName = "";

    @JSONField(ordinal = 3)
    private String idNo = "";

    @JSONField(ordinal = 4)
    private String phone;

    @JSONField(ordinal = 5)
    private String merchantName = "";

    @JSONField(ordinal = 6)
    private String customerNo;

    @JSONField(ordinal = 7)
    private String serialNo = "";

    @JSONField(ordinal = 8)
    private String smsCode = "";

    @JSONField(ordinal = 9)
    private String noSMS = "true";

    public String getMerType() {
        return merType;
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public String getNoSMS() {
        return noSMS;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
