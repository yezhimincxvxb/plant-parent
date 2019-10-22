package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 菌包汇总信息
 */
public class SeedCountInfo implements Serializable {

    @JSONField(name = "seed_name")
    private String seedName;

    @JSONField(name = "left_count")
    private String leftCount;

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public String getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(String leftCount) {
        this.leftCount = leftCount;
    }
}
