package com.moguying.plant.core.entity.payment.response;

public class PayResponse implements PaymentResponseInterface {
    private String merOrderNo;

    public String getMerOrderNo() {
        return merOrderNo;
    }

    public void setMerOrderNo(String merOrderNo) {
        this.merOrderNo = merOrderNo;
    }
}
