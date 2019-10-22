package com.moguying.plant.core.constant;

public enum PaymentCallbackEnum {
    REGISTER_CALLBACK("/register");

    private String host = "http://127.0.0.1:9000/payment/callback";
    private String action;

    PaymentCallbackEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return host + action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
