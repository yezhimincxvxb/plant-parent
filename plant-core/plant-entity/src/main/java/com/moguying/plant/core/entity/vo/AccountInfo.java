package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountInfo implements Serializable {

    /**
     * 第三方支付商户号
     */
    private String paymentAccount;


    /**
     * 资产总计
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    /**
     * 可用资产
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal availableAmount;


    /**
     * 累计收入
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal accruedProfit;

    /**
     * 本月收益
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal monthProfit;

}
