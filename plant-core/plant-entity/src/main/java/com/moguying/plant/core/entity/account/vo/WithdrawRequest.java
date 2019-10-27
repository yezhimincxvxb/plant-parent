package com.moguying.plant.core.entity.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WithdrawRequest implements Serializable {

    private static final long serialVersionUID = -4306427271971509256L;

    private Integer bankId;

    private BigDecimal money;

    private String code;

    /**
     * 在到账时使用
     */
    private Integer withdrawId;
    /**
     * 在到账时使用
     */
    private String smsCode;
    /**
     * 在到账时使用
     */
    private String seqNo;

    /**
     * 在到账时使用
     */
    private String orderNumber;


}
