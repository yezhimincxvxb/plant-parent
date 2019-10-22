package com.moguying.plant.core.entity.dto.payment.response;

public class RealnameSyncResponse implements PaymentResponseInterface {

    private String type;

    private String sonMerNo;

    private String legalPersonName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
