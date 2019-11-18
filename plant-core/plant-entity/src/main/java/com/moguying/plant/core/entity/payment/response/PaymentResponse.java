package com.moguying.plant.core.entity.payment.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
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
    private T data;
}
