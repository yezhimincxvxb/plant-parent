package com.moguying.plant.core.entity.vo;

import java.io.Serializable;

public class BuyOrder implements Serializable {


    /**
     * 购买份数
     */
    private Integer count;

    /**
     * 购买菌包id
     */
    private Integer seedId;


    /**
     * 是否不使用余额支付
     */
    private Boolean isPayByAccount;


    public Boolean getPayByAccount() {
        return isPayByAccount;
    }

    public void setPayByAccount(Boolean payByAccount) {
        isPayByAccount = payByAccount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }
}
