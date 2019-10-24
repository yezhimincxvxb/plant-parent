package com.moguying.plant.constant;

public enum FarmerEnum {
    NOTICE_READ(1,"已读"),
    NOTICE_NO_READ(0,"未读"),
    ENERGY_NO_GET(0,"未采摘"),
    ENERGY_HAS_GET(1,"已采摘"),
    ENERGY_OUT_TIME(2,"已失效"),
    ENERGY_NO_GROW(0,"未成熟"),
    ENERGY_HAS_GROW(1,"已成熟"),
    ENERGY_HAS_LOSE(2,"已过期");
    private Integer state;
    private String stateStr;

    FarmerEnum(Integer state, String stateStr) {
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
