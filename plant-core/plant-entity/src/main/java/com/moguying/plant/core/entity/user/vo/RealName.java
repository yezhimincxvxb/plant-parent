package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.IdCardSerialize;

import java.io.Serializable;

public class RealName implements Serializable {

    @JSONField(ordinal = 1)
    private String name;

    @JSONField(serializeUsing = IdCardSerialize.class,ordinal = 2)
    private String idCard;

    @JSONField(ordinal = 3)
    private Integer state;

    @JSONField(ordinal = 4)
    private String stateStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }
}
