package com.moguying.plant.constant;

public enum OrderPrefixEnum {
    SEED_ORDER("O"),
    SEED_ORDER_DETAIL("D"),
    RECHARGE_ORDER("R"),
    WITHDRAW_ORDER("W"),
    PLANT_ORDER("P"),
    MALL_PRODUCT_ORDER("MP"),
    PAYMENT_BIND_CARD_ORDER("PBC"),
    KAN_JIA("KJ"),
    TI_YAN_BUY("TYB"),
    FREE_JUN_BAO("FJB"),
    INVITE_REWARD("IR"),
    FRIEND_HELP("FH"),
    RED_PACKAGE("RP");


    private String preFix;

    OrderPrefixEnum(String preFix) {
        this.preFix = preFix;
    }


    public String getPreFix() {
        return preFix;
    }

    public void setPreFix(String preFix) {
        this.preFix = preFix;
    }


}
