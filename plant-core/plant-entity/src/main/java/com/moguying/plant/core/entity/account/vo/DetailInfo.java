package com.moguying.plant.core.entity.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.mall.vo.ProductInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 明细详情
 */
@Data
public class DetailInfo implements Serializable {

    private static final long serialVersionUID = 4067138541845911215L;

    /**
     * 交易金额
     */
    @JSONField(ordinal = 1)
    private BigDecimal transactionMoney;

    /**
     * 交易时间
     */
    @JSONField(ordinal = 2, format = "yyyy-MM-dd hh:mm:ss")
    private Date transactionTime;

    /**
     * 交易类型(业务摘要)
     */
    @JSONField(ordinal = 3)
    private String transactionType;

    /**
     * 交易方式
     */
    @JSONField(ordinal = 4)
    private String transactionMode;

    /**
     * 商品详情
     */
    @JSONField(ordinal = 5)
    private List<ProductInfo> productInfoList;

    public String getTransactionMoney() {
        String result = new DecimalFormat("0.00").format(transactionMoney);
        if (result.startsWith("-"))
            return result;

        return "+" + result;
    }
}
