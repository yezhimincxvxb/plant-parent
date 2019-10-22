package com.moguying.plant.core.entity.dto.payment.callback;

/**
 * 对第三方支付的请求响应
 */
public class CallBackResponseToPayment {

    private String code;

    private String msg;


    public CallBackResponseToPayment(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
