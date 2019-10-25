package com.moguying.plant.core.entity.payment.callback;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.payment.response.PaymentResponseInterface;
import lombok.Data;

/**
 * 第三方主动通知的请求参数
 */
@Data
public class CallBackResponse implements PaymentResponseInterface {

    @JSONField(ordinal = 1)
    private String merMerOrderNo;

    @JSONField(ordinal = 2)
    private String payAmount;

    @JSONField(ordinal = 3)
    private String orderNo;

    @JSONField(ordinal = 4)
    private String tradeFee;

    @JSONField(ordinal = 5)
    private String sellerNo;

    @JSONField(ordinal = 6)
    private String buyerNo;

    @JSONField(ordinal = 7)
    private String channelType;
}
