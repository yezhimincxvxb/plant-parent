package com.moguying.plant.constant;

public enum BlockStateEnum {
    NO_OPEN("未开放", 0),
    OPEN("已开放", 1),

    GROW_DAYS_30("30天生长周期", 30),
    GROW_DAYS_45("30天生长周期", 45),
    GROW_DAYS_60("30天生长周期", 60);

    private String name;
    private Integer state;

    BlockStateEnum(String name, Integer state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
