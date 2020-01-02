package com.moguying.plant.core.entity;

import com.moguying.plant.utils.annotation.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SendMessage implements Serializable {

    private static final long serialVersionUID = -7130925525808342041L;

    @IsMobile
    private String phone;

    @NotNull(message = "用途参数不能为空")
    private Boolean isReg;

    private String imageCode;

}
