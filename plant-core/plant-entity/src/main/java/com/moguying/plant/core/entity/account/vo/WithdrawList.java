package com.moguying.plant.core.entity.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawList implements Serializable {

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal withdrawSum;


    private PageResult<MoneyWithdraw> pageResult;

    public BigDecimal getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(BigDecimal withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public PageResult<MoneyWithdraw> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<MoneyWithdraw> pageResult) {
        this.pageResult = pageResult;
    }
}
