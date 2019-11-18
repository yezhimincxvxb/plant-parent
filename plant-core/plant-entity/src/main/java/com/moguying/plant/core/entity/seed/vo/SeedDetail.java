package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.seed.SeedContent;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class SeedDetail implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String seedName;

    @JSONField(ordinal = 3)
    private String picUrl;

    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perPrice;

    @JSONField(ordinal = 5)
    private Integer leftCount;

    //生长周期
    @JSONField(ordinal = 6)
    private Integer growDays;

    @JSONField(ordinal = 7)
    private SeedContent content;

    public Integer getGrowDays() {
        return growDays;
    }

    public void setGrowDays(Integer growDays) {
        this.growDays = growDays;
    }

    public BigDecimal getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(BigDecimal perPrice) {
        this.perPrice = perPrice;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public SeedContent getContent() {
        return content;
    }

    public void setContent(SeedContent content) {
        this.content = content;
    }
}
