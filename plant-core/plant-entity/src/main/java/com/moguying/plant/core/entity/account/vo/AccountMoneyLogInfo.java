package com.moguying.plant.core.entity.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountMoneyLogInfo implements Serializable {

    private static final long serialVersionUID = -6301784383682246471L;


    @JSONField(ordinal = 1)
    private String year;

    @JSONField(ordinal = 2)
    private String month;

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 3)
    private BigDecimal thisMonthInAccount;

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 4)
    private BigDecimal thisMonthOutAccount;

    @JSONField(ordinal = 5)
    private List<String> hasDataDays;

}
