package com.moguying.plant.core.entity.dto;

import java.util.List;

/**
 * 券的使用
 */
public class UseFertilizer {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 券id,可多张使用
     */
    private List<Integer> fertilizerIds;

    /**
     * 商城订单id
     */
    private Integer mallOrderId;

    /**
     * 菌包订单id
     */
    private Integer seedOrderId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getFertilizerIds() {
        return fertilizerIds;
    }

    public void setFertilizerIds(List<Integer> fertilizerIds) {
        this.fertilizerIds = fertilizerIds;
    }

    public Integer getMallOrderId() {
        return mallOrderId;
    }

    public void setMallOrderId(Integer mallOrderId) {
        this.mallOrderId = mallOrderId;
    }

    public Integer getSeedOrderId() {
        return seedOrderId;
    }

    public void setSeedOrderId(Integer seedOrderId) {
        this.seedOrderId = seedOrderId;
    }
}
