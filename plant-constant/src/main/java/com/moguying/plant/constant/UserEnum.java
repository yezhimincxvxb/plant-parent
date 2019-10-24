package com.moguying.plant.constant;

public enum UserEnum {
    BANK_IN_USED(1,"使用中"),
    BANK_NOT_USED(0,"未使用"),

    REG_SOURCE_PC(1,"PC"),
    REG_SOURCE_MOBILE(1,"MOBILE"),

    USER_ACTIVE(1,"正常"),
    USER_IS_BAND(0,"冻结"),

    USER_PAYMENT_ACCOUNT_VERIFY_ERROR(0,"认证失败"),
    USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS(1,"认证成功"),
    USER_PAYMENT_ACCOUNT_NEED_VERIFY(2,"未认证"),
    USER_PAYMENT_ACCOUNT_VERIFY_ING(3,"审核中"),

    USER_PAYMENT_ACCOUNT_UN_REGISTER(0,"未注册"),
    USER_PAYMENT_ACCOUNT_REGISTER(1,"已注册");


    private Integer state;
    private String stateName;

    UserEnum(Integer state, String stateName) {
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
