package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

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

    @JSONField(name = "IDValidity",ordinal = 8)
    private String IDValidity = "";

    @JSONField(ordinal = 9)
    private String legalPersonIdphotoa ;

    @JSONField(ordinal = 10)
    private String legalPersonIdphotob;

    @JSONField(ordinal = 11)
    private String merchantName = "";


    public String getType() {
        return type;
    }

    public String getTypeOfID() {
        return typeOfID;
    }

    public String getSonMerNo() {
        return sonMerNo;
    }

    public void setSonMerNo(String sonMerNo) {
        this.sonMerNo = sonMerNo;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonPhone() {
        return legalPersonPhone;
    }

    public void setLegalPersonPhone(String legalPersonPhone) {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getLegalPersonIdnum() {
        return legalPersonIdnum;
    }

    public void setLegalPersonIdnum(String legalPersonIdnum) {
        this.legalPersonIdnum = legalPersonIdnum;
    }

    public String getLongTimeOrNoPer() {
        return longTimeOrNoPer;
    }

    public void setLongTimeOrNoPer(String longTimeOrNoPer) {
        this.longTimeOrNoPer = longTimeOrNoPer;
    }

    public String getIDValidity() {
        return IDValidity;
    }

    public void setIDValidity(String IDValidity) {
        this.IDValidity = IDValidity;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getLegalPersonIdphotoa() {
        return legalPersonIdphotoa;
    }

    public void setLegalPersonIdphotoa(String legalPersonIdphotoa) {
        this.legalPersonIdphotoa = legalPersonIdphotoa;
    }

    public String getLegalPersonIdphotob() {
        return legalPersonIdphotob;
    }

    public void setLegalPersonIdphotob(String legalPersonIdphotob) {
        this.legalPersonIdphotob = legalPersonIdphotob;
    }
}
