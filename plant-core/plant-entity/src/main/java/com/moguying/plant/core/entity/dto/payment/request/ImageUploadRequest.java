package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class ImageUploadRequest implements PaymentRequestInterface {


    @JSONField(ordinal = 1)
    private String merchantNo;

    @JSONField(ordinal = 2)
    private String image;

    @JSONField(ordinal = 3)
    private String description = "";

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
