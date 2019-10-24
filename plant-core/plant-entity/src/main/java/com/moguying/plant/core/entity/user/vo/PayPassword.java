package com.moguying.plant.core.entity.user.vo;

import java.io.Serializable;

public class PayPassword implements Serializable {


    private String oldPayPassword;

    private String payPassword;

    private String code;


    public String getOldPayPassword() {
        return oldPayPassword;
    }

    public void setOldPayPassword(String oldPayPassword) {
        this.oldPayPassword = oldPayPassword;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
