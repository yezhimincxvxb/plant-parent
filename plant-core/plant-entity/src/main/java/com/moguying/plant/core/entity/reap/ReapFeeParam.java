package com.moguying.plant.core.entity.reap;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("plant_reap_fee_param")
public class ReapFeeParam implements Serializable {

    private static final long serialVersionUID = -8486863392389013396L;

    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private Integer id;

    @TableField
    @JSONField(ordinal = 2)
    private Integer inviteUid;

    @TableField
    @JSONField(ordinal = 3)
    private BigDecimal firstPlantRate;

    @TableField
    @JSONField(ordinal = 4)
    private BigDecimal plantRate;

    @TableField
    @JSONField(ordinal = 5)
    private Integer seedType;

    @TableField
    @TableLogic(value = "0",delval = "1")
    @JSONField(serialize = false)
    private Boolean isDelete;


    @JSONField(ordinal = 6)
    @TableField(exist = false)
    private String phone;

    @JSONField(ordinal = 7)
    @TableField(exist = false)
    private String seedTypeName;


}