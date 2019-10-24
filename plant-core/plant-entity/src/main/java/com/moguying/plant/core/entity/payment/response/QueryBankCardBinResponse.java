package com.moguying.plant.core.entity.payment.response;

public class QueryBankCardBinResponse implements PaymentResponseInterface {
    private String bankCode;

    private String bankName;

    private String bankNumber;

    private String unionNumbers;

    private String dfSwitch;

    private String csSwitch;

    private String b2bSwitch;

    private String xyFpSwitch;

    private String xyFpCreditSwitch;

    private String bingPerUnionNumbers;

    private String cardType;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getUnionNumbers() {
        return unionNumbers;
    }

    public void setUnionNumbers(String unionNumbers) {
        this.unionNumbers = unionNumbers;
    }

    public String getDfSwitch() {
        return dfSwitch;
    }

    public void setDfSwitch(String dfSwitch) {
        this.dfSwitch = dfSwitch;
    }

    public String getCsSwitch() {
        return csSwitch;
    }

    public void setCsSwitch(String csSwitch) {
        this.csSwitch = csSwitch;
    }

    public String getB2bSwitch() {
        return b2bSwitch;
    }

    public void setB2bSwitch(String b2bSwitch) {
        this.b2bSwitch = b2bSwitch;
    }

    public String getXyFpSwitch() {
        return xyFpSwitch;
    }

    public void setXyFpSwitch(String xyFpSwitch) {
        this.xyFpSwitch = xyFpSwitch;
    }

    public String getXyFpCreditSwitch() {
        return xyFpCreditSwitch;
    }

    public void setXyFpCreditSwitch(String xyFpCreditSwitch) {
        this.xyFpCreditSwitch = xyFpCreditSwitch;
    }

    public String getBingPerUnionNumbers() {
        return bingPerUnionNumbers;
    }

    public void setBingPerUnionNumbers(String bingPerUnionNumbers) {
        this.bingPerUnionNumbers = bingPerUnionNumbers;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "QueryBankCardBinResponse{" +
                "bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", unionNumbers='" + unionNumbers + '\'' +
                ", dfSwitch='" + dfSwitch + '\'' +
                ", csSwitch='" + csSwitch + '\'' +
                ", b2bSwitch='" + b2bSwitch + '\'' +
                ", xyFpSwitch='" + xyFpSwitch + '\'' +
                ", xyFpCreditSwitch='" + xyFpCreditSwitch + '\'' +
                ", bingPerUnionNumbers='" + bingPerUnionNumbers + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
