package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * plant_seed_content
 * @author 
 */
public class SeedContent implements Serializable {
    @JSONField(serialize = false)
    private Integer seedType;

    private String seedIntroduce;

    private String contractContent;

    private static final long serialVersionUID = 1L;

    public Integer getSeedType() {
        return seedType;
    }

    public void setSeedType(Integer seedType) {
        this.seedType = seedType;
    }

    public String getSeedIntroduce() {
        return seedIntroduce;
    }

    public void setSeedIntroduce(String seedIntroduce) {
        this.seedIntroduce = seedIntroduce;
    }

    public String getContractContent() {
        return contractContent;
    }

    public void setContractContent(String contractContent) {
        this.contractContent = contractContent;
    }
}