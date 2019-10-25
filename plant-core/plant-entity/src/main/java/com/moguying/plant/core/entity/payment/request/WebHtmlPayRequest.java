package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class WebHtmlPayRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String sellerNo;

    @JSONField(ordinal = 2)
    private String payChannels;

    @JSONField(ordinal = 3)
    private String authCodes = "";

    @JSONField(ordinal = 4)
    private String orderBody;

    @JSONField(ordinal = 5)
    private String payAmount;

    @JSONField(ordinal = 6)
    private String apiPayType = "1";

    @JSONField(ordinal = 7)
    private String tradeType = "0";

    @JSONField(ordinal = 8)
    private String merMerOrderNo;

    @JSONField(ordinal = 9)
    private String buyerNo;

    @JSONField(ordinal = 10)
    private String undiscountableAmount = "";

    @JSONField(ordinal = 11)
    private String orderSubject;

    @JSONField(ordinal = 12)
    private String tempRoutingList = "";

    @JSONField(ordinal = 13)
    private String returnUrl;

    @JSONField(ordinal = 14)
    private String ptUndertakeRate = "";

}
