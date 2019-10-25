package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TransferRequest implements PaymentRequestInterface {

    /**
     * 收款方商户号
     */
    @JSONField(ordinal = 1)
    private String payeeNo;

    /**
     * 填写姓名
     */
    @JSONField(ordinal = 2)
    private String payeeName;


    /**
     * 金额
     */
    @JSONField(ordinal = 3)
    private String amount;

    /**
     * 收款方已绑卡号
     */
    @JSONField(ordinal = 4)
    private String cardNo;

    /**
     * 备注
     */
    @JSONField(ordinal = 5)
    private String remarks = "";
}
