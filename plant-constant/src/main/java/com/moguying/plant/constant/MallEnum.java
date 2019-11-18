package com.moguying.plant.constant;

public enum MallEnum {
    ORDER_NEED_PAY(0, "待付款"),
    ORDER_HAS_PAY(1, "待发货"),
    ORDER_HAS_SEND(2, "待收货"),
    ORDER_HAS_DONE(3, "已完成"),
    ORDER_HAS_CLOSE(4, "已关闭"),
    ORDER_HAS_CANCEL(5, "已取消");
    private Integer state;

    private String stateStr;


    MallEnum(Integer state, String stateStr) {
        this.state = state;
        this.stateStr = stateStr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }
}
