package com.moguying.plant.core.entity.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 出入金
 */
@Data
public class InAndOutMoney implements Serializable {

    private static final long serialVersionUID = 1162189668039527379L;

    /**
     * 收入
     */
    private String inCome;

    /**
     * 支出
     */
    private String outPay;

    public InAndOutMoney(String inCome, String outPay) {
        this.inCome = inCome;
        this.outPay = outPay;
    }
}
