package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户首页摘要信息
 */
@Data
public class UserSummaryInfo implements Serializable {

    @JSONField(ordinal = 1)
    private Integer seedCount = 0;

    @JSONField(ordinal = 2)
    private Integer blockCount = 0;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 3)
    private BigDecimal availableAmount;

    @JSONField(ordinal = 4)
    private String phone;

    @JSONField(ordinal = 5)
    private boolean hasNewMessage;

    @JSONField(ordinal = 6)
    private boolean hasPayPassword;

}
