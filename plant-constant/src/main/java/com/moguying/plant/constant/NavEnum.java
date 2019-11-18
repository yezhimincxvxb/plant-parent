package com.moguying.plant.constant;

public enum NavEnum {
    PLANT_TYPE(1, "种植平台"),
    MALL_TYPE(2, "商城");

    NavEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


    private Integer type;
    private String typeName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
