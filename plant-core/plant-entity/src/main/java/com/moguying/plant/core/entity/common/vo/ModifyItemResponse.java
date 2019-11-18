package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ModifyItemResponse implements Serializable {

    public ModifyItemResponse(BigDecimal checkedAmount) {
        this(checkedAmount, false);
    }

    public ModifyItemResponse(BigDecimal checkedAmount, Boolean checkAll) {
        this.checkedAmount = checkedAmount;
        this.checkAll = checkAll;
    }

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 1)
    private BigDecimal checkedAmount = BigDecimal.ZERO;

    @JSONField(ordinal = 2)
    private Boolean checkAll = false;

}
