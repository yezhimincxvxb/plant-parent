package com.moguying.plant.core.entity.payment.callback;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 对第三方支付的请求响应
 */
@Data
@AllArgsConstructor
public class CallBackResponseToPayment {

    private String code;

    private String msg;
}
