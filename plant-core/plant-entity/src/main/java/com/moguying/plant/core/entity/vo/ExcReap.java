package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class ExcReap {

    @JSONField(name = "count")
    private Integer excCount;
    @JSONField(name = "address")
    private String extAddress;
    @JSONField(name = "fee")
    private String extFee;

    public Integer getExcCount() {
        return excCount;
    }

    public void setExcCount(Integer excCount) {
        this.excCount = excCount;
    }

    public String getExtAddress() {
        return extAddress;
    }

    public void setExtAddress(String extAddress) {
        this.extAddress = extAddress;
    }

    public String getExtFee() {
        return extFee;
    }

    public void setExtFee(String extFee) {
        this.extFee = extFee;
    }
}
