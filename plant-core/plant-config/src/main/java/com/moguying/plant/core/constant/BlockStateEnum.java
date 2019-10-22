package com.moguying.plant.core.constant;

public enum BlockStateEnum {
    NO_OPEN("未开放",0),
    OPEN("已开放",1);

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
