package com.moguying.plant.constant;

public enum ApiEnum {
    AUTH_TOKEN(2,"Auth-Token"),
    REFRESH_AUTH_TOKEN(3,"Auth-Token-Re"),

    LOGIN_USER_ID(1,"userId"),
    LOGIN_PHONE(3,"phone"),
    LOGIN_TIME(4,"loginTime"),

    TASTE_OPEN(1,"进行中"),
    TASTE_CLOSE(2,"已结束"),

    TASTE_APPLY(0,"试吃资格申请"),
    TASTE_APPLY_SUCCESS(1,"试吃资格成功"),
    TASTE_APPLY_FAILED(1,"试吃资格失败");


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
