package com.moguying.plant.constant;

public enum FertilizerEnum {
    FERTILIZER_NO_USE(0, "未使用"),
    FERTILIZER_USED(1, "已使用"),
    FERTILIZER_OUT_TO_EXPIRE(2, "已过期"),

    COUPON_FERTILIZER(1, "优卖券"),
    FULL_FERTILIZER(2, "满减券"),
    PLANT_FERTILIZER(3, "种植券");

    private Integer state;
    private String stateName;

    FertilizerEnum(Integer state, String stateName) {
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
