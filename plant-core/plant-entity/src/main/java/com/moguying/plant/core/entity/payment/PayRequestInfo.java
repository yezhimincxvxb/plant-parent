package com.moguying.plant.core.entity.payment;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayRequestInfo implements Serializable {

    private static final long serialVersionUID = 8294491161918982065L;

    private Integer userId;

    //选对哪张银行卡支付
    private Integer bankId;

    private String money;

    private String merOrderNo;

    private String orderSubject;

    //发送短信后
    private String smsCode;
    //发送短信后
    private String seqNo;
}
