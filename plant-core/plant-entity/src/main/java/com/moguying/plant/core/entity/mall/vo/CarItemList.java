package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.math.BigDecimal;
import java.util.List;

public class CarItemList {
    @JSONField(ordinal = 1)
    private List<OrderItem> items;

    @JSONField(ordinal = 2)
    private Integer count;


    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 3)
    private BigDecimal checkedAmount;

    @JSONField(ordinal = 4)
    private Boolean checkAll;

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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
