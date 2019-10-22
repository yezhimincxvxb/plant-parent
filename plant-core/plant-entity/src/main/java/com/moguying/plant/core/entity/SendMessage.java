package com.moguying.plant.core.entity;

import java.io.Serializable;

public class SendMessage implements Serializable {

    private String phone;

    private String code;


    private Boolean isReg;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsReg() {
        return isReg;
    }

    public void setIsReg(Boolean isReg) {
        this.isReg = isReg;
    }


}
