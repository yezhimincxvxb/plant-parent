package com.moguying.plant.constant;

public enum MoneyStateEnum {
    RECHARGING(0,"充值中"),
    RECHARGE_SUCCESS(1,"充值成功"),
    RECHARGE_FAILED(2,"充值失败"),

    WITHDRAWING(0,"提现中"),
    WITHDRAW_SUCCESS(1,"提现成功"),
    WITHDRAW_FAILED(2,"提现失败"),
    WITHDRAW_IN_ACCOUNT(3,"提现到账"),
    WITHDRAW_ACCOUNT_ING(4,"提现到账中");


    MoneyStateEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    private Integer state;

    private String stateInfo;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
