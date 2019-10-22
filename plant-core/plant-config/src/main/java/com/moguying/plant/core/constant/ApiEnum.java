package com.moguying.plant.core.constant;

public enum ApiEnum {
    AUTH_TOKEN(2,"Auth-Token"),
    REFRESH_AUTH_TOKEN(3,"Auth-Token-Re"),

    LOGIN_USER_ID(1,"userId"),
    LOGIN_PHONE(3,"phone"),
    LOGIN_TIME(4,"loginTime");


    private Integer type;

    private String typeStr;

    ApiEnum(Integer type, String typeStr) {
        this.type = type;
        this.typeStr = typeStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
