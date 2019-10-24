package com.moguying.plant.core.entity.seed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_seed_inner_order
 * @author 
 */
public class SeedInnerOrder implements Serializable {
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 种子id
     */
    private Integer seedId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 棚区id
     */
    private Integer blockId;

    /**
     * 购买种子份数
     */
    private Integer plantCount;

    /**
     * 购买总价
     */
    private BigDecimal plantAmount;

    /**
     * 种植利润
     */
    private BigDecimal plantProfit;

    /**
     * 种子订单状态[0已购买未种植，1已种植,2已取消]
     */
    private Boolean plantState;

    /**
     * 种植时间
     */
    private Date plantTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(Integer plantCount) {
        this.plantCount = plantCount;
    }

    public BigDecimal getPlantAmount() {
        return plantAmount;
    }

    public void setPlantAmount(BigDecimal plantAmount) {
        this.plantAmount = plantAmount;
    }

    public BigDecimal getPlantProfit() {
        return plantProfit;
    }

    public void setPlantProfit(BigDecimal plantProfit) {
        this.plantProfit = plantProfit;
    }

    public Boolean getPlantState() {
        return plantState;
    }

    public void setPlantState(Boolean plantState) {
        this.plantState = plantState;
    }

    public Date getPlantTime() {
        return plantTime;
    }

    public void setPlantTime(Date plantTime) {
        this.plantTime = plantTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}