package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class UserSeedOrder {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String seedTypeName;

    @JSONField(ordinal = 3)
    private Integer count;

    @JSONField(ordinal = 4)
    private String picUrl;

    @JSONField(ordinal = 5)
    private Integer seedTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeedTypeName() {
        return seedTypeName;
    }

    public void setSeedTypeName(String seedTypeName) {
        this.seedTypeName = seedTypeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getSeedTypeId() {
        return seedTypeId;
    }

    public void setSeedTypeId(Integer seedTypeId) {
        this.seedTypeId = seedTypeId;
    }
}
