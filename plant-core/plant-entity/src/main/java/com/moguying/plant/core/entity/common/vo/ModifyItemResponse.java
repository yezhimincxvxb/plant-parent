package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.math.BigDecimal;

public class ModifyItemResponse {

    public ModifyItemResponse() {
    }

    public ModifyItemResponse(BigDecimal checkedAmount) {
        this(checkedAmount,false);
    }

    public ModifyItemResponse(BigDecimal checkedAmount, Boolean checkAll) {
        this.checkedAmount = checkedAmount;
        this.checkAll = checkAll;
    }

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 1)
    private BigDecimal checkedAmount = BigDecimal.ZERO;

    @JSONField(ordinal = 2)
    private Boolean checkAll = false;

    public BigDecimal getCheckedAmount() {
        return checkedAmount;
    }

    public void setCheckedAmount(BigDecimal checkedAmount) {
        this.checkedAmount = checkedAmount;
    }

    public Boolean getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(Boolean checkAll) {
        this.checkAll = checkAll;
    }
}
