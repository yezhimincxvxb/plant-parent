package com.moguying.plant.core.constant;

public enum FertilizerEnum {
    FERTILIZER_NO_USE(0,"未使用"),
    FERTILIZER_USED(1,"已使用"),
    FERTILIZER_OUT_TO_EXPIRE(2,"已过期");
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
