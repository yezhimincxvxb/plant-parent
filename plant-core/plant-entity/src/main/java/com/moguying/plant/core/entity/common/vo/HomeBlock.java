package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class HomeBlock {
    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String number;

    @JSONField(ordinal = 3)
    private String seedTypeName;

    @JSONField(ordinal = 4)
    private String picUrl;

}
