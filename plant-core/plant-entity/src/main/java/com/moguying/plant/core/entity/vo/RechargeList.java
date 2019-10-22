package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.MoneyRecharge;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class RechargeList  implements Serializable {

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal  sumRecharge;



    private PageResult<MoneyRecharge> pageResult;

    public BigDecimal getSumRecharge() {
        return sumRecharge;
    }

    public void setSumRecharge(BigDecimal sumRecharge) {
        this.sumRecharge = sumRecharge;
    }

    public PageResult<MoneyRecharge> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<MoneyRecharge> pageResult) {
        this.pageResult = pageResult;
    }
}
