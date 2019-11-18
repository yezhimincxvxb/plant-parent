package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SeedInnerOrderCount implements Serializable {

    private static final long serialVersionUID = -6529928976614946727L;

    @JSONField(ordinal = 1)
    private Integer seedId;

    @JSONField(ordinal = 2)
    private String seedName;

    @JSONField(ordinal = 3)
    private Integer orderCount;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 4)
    private Date orderTime;
}
