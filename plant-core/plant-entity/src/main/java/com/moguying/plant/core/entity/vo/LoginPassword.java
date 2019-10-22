package com.moguying.plant.core.entity.vo;

import java.io.Serializable;

public class LoginPassword  implements Serializable {

    private String oldPassword;

    private String password;

    private String code;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
