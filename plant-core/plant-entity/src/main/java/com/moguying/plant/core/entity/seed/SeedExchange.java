package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * plant_seed_exchange
 * @author 
 */
public class SeedExchange implements Serializable {
    private Integer id;

    /**
     * 种子id
     */
    @JSONField(name = "seed_id")
    private Integer seedId;

    /**
     * 菌包名称
     */
    @JSONField(name = "seed_name")
    private String seedName;


    /**
     * 种植用户id
     */
    @JSONField(name = "user_id")
    private Integer userId;

    /**
     * 用户名
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     * 兑换份数
     */
    @JSONField(name = "exchange_count")
    private Integer exchangeCount;

    /**
     * 快递记录id
     */
    @JSONField(name = "express_id")
    private Integer expressId;

    /**
     * [0未收货，1已收货]
     */
    private Integer state;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(Integer exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}