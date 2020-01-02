package com.moguying.plant.core.entity.admin.vo;

import com.moguying.plant.utils.annotation.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminUserLogin {

    @IsMobile
    private String phone;

    @NotNull(message = "短信验证码不能为空")
    private String code;


}
