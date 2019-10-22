package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * plant_nav
 * @author 
 */
public class Nav implements Serializable {

    private Integer id;

    private String name;

    @JSONField(name = "jump_url")
    private String jumpUrl;

    /**
     * 是否显示[0否，1是]
     */
    @JSONField(name = "is_show")
    private Boolean isShow;

    /**
     * 是否新窗口打开[0否，1是]
     */
    @JSONField(name = "is_blank")
    private Boolean isBlank;

    @JSONField(name = "order_number")
    private Integer orderNumber;

    /**
     * 类型[1种植平台，2商城平台]
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsBlank() {
        return isBlank;
    }

    public void setIsBlank(Boolean isBlank) {
        this.isBlank = isBlank;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}