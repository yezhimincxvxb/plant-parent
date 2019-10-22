package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user_address
 * @author 
 */
public class UserAddress implements Serializable {

    @JSONField(deserialize = false,ordinal = 1)
    private Integer id;

    @JSONField(deserialize = false,ordinal = 2)
    private Integer userId;

    /**
     * 省份id
     */
    @JSONField(ordinal = 3)
    private String provinceName;

    /**
     * 市id
     */
    @JSONField(ordinal = 4)
    private String cityName;

    /**
     * 县id
     */
    @JSONField(ordinal = 5)
    private String townName;

    /**
     * 收货人名
     */
    @JSONField(ordinal = 6)
    private String receiveUserName;

    /**
     * 收货人手机号
     */
    @JSONField(ordinal = 7)
    private String receivePhone;

    /**
     * 地址详情
     */
    @JSONField(ordinal = 8)
    private String detailAddress;

    @JSONField(ordinal = 9,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JSONField(name = "isDefault",ordinal = 10)
    private Boolean isDefault;

    @JSONField(name = "isDelete",ordinal = 11)
    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}