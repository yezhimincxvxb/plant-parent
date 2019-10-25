package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class HomeBlock implements Serializable {

    private static final long serialVersionUID = -2982041036342492259L;

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String number;

    @JSONField(ordinal = 3)
    private String seedTypeName;

    @JSONField(ordinal = 4)
    private String picUrl;

}
