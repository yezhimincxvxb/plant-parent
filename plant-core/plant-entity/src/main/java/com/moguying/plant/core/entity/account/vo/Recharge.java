package com.moguying.plant.core.entity.account.vo;

import java.io.Serializable;

public class Recharge implements Serializable {

    private static final long serialVersionUID = -1913394494115529820L;

    private Integer id;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
