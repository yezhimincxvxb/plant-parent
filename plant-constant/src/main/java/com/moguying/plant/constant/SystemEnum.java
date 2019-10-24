package com.moguying.plant.constant;

public enum SystemEnum {
    PHONE_MESSAGE_NO_VALIDATE(0,"未校验"),
    PHONE_MESSAGE_VALIDATE(1,"已校验"),

    PHONE_MESSAGE_SEED_BUY_TYPE(1,"seedBuy"),
    PHONE_MESSAGE_SEED_PLANTED_TYPE(2,"plant"),
    PHONE_MESSAGE_MALL_BUY_TYPE(7,"productBuy"),
    PHONE_MESSAGE_SEED_REAP_TYPE(3,"菌包采摘"),
    PHONE_MESSAGE_WITHDRAW_SUCCESS_TYPE(4,"提现成功"),
    PHONE_MESSAGE_WITHDRAW_FAIL_TYPE(5,"提现失败"),
    PHONE_MESSAGE_MALL_SEND(6,"商城发货");

    private Integer state;
    private String stateName;

    SystemEnum(Integer state, String stateName) {
        this.state = state;
        this.stateName = stateName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
