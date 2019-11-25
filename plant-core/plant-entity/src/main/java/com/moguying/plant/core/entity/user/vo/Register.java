package com.moguying.plant.core.entity.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Register implements Serializable {

    private static final long serialVersionUID = 7703429594196777211L;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 短信验证码
     */
    private String msgCode;

    /**
     * 邀请码
     */
    private String inviteCode;
}
