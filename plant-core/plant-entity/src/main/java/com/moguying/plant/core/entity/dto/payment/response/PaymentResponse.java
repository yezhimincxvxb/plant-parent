package com.moguying.plant.core.entity.dto.payment.response;

import com.alibaba.fastjson.annotation.JSONField;

public class PaymentResponse<T extends PaymentResponseInterface> {

    @JSONField(ordinal = 1)
    private String code;

    @JSONField(ordinal = 2)
    private String msg;

    @JSONField(ordinal = 3)
    private String responseType;

    @JSONField(ordinal = 4)
    private String responseParameters;

    private String sign;

    /**
     * 辅助字段，因第三方传回的responseParameters是字符串，直
     * 接无法转对象
     */
    private T data ;


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

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseParameters() {
        return responseParameters;
    }

    public void setResponseParameters(String responseParameters) {
        this.responseParameters = responseParameters;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", responseType='" + responseType + '\'' +
                ", responseParameters='" + responseParameters + '\'' +
                ", sign='" + sign + '\'' +
                ", data=" + data +
                '}';
    }
}
