package com.moguying.plant.core.entity.dto.payment.response;

public class WithdrawMoneyResponse implements PaymentResponseInterface {

    /**
     * API商户订单号
     */
    private String merMerOrderNo;

    /**
     * 双乾订单号
     */
    private String orderNo;

    /**
     * 提现金额
     */
    private String amount;


    public String getMerMerOrderNo() {
        return merMerOrderNo;
    }

    public void setMerMerOrderNo(String merMerOrderNo) {
        this.merMerOrderNo = merMerOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
