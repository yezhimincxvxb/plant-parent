package com.moguying.plant.core.entity.dto.payment.response;

public class RegisterAsyncResponse implements PaymentResponseInterface {

    private String merType;

    private String merNo;

    private String sonMerNo;

    private String status;

    private String seraialNumber;


    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSonMerNo() {
        return sonMerNo;
    }

    public void setSonMerNo(String sonMerNo) {
        this.sonMerNo = sonMerNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeraialNumber() {
        return seraialNumber;
    }

    public void setSeraialNumber(String seraialNumber) {
        this.seraialNumber = seraialNumber;
    }
}
