package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ImageUploadRequest implements PaymentRequestInterface {


    @JSONField(ordinal = 1)
    private String merchantNo;

    @JSONField(ordinal = 2)
    private String image;

    @JSONField(ordinal = 3)
    private String description = "";
}
