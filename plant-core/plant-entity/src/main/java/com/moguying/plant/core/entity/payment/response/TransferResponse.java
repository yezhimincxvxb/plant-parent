package com.moguying.plant.core.entity.payment.response;

public class TransferResponse implements PaymentResponseInterface{

    private String payeeNo;

    private String amount;

    private String cardNo;

    public String getPayeeNo() {
        return payeeNo;
    }

    public void setPayeeNo(String payeeNo) {
        this.payeeNo = payeeNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
