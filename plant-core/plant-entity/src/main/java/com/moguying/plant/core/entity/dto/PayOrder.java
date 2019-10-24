package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MoneyOpEnum;

import java.math.BigDecimal;
import java.util.Date;

public interface PayOrder {
    Integer getId();

    void setId(Integer id);

    @JSONField(serialize = false)
    MoneyOpEnum getOpType();

    Date getPayTime();

    Integer getState();

    BigDecimal getBuyAmount();

    BigDecimal getFeeAmount();

    String getOrderNumber();

    BigDecimal getAccountPayAmount();

    void setAccountPayAmount(BigDecimal accountPayAmount);

    BigDecimal getCarPayAmount();

    void setCarPayAmount(BigDecimal carPayAmount);

    void setSeqNo(String seqNo);

    BigDecimal getReducePayAmount();

    void setReducePayAmount(BigDecimal reducePayAmount);
}
