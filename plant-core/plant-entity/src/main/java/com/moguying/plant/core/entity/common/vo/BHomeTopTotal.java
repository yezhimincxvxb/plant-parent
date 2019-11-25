package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BHomeTopTotal implements Serializable {

    private static final long serialVersionUID = -5871494341719662575L;
    /**
     * 注册人数
     */
    @JSONField(ordinal = 1)
    private Integer regNum;
    /**
     * 种植额度
     */
    @JSONField(ordinal = 2,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantLines;
    /**
     * 种植利润
     */
    @JSONField(ordinal = 3,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantProfits;

    /**
     * 订单总笔数
     */
    @JSONField(ordinal = 4)
    private Integer orderNum;

}
