package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class UserSeedOrder {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String seedTypeName;

    @JSONField(ordinal = 3)
    private Integer count;

    @JSONField(ordinal = 4)
    private String picUrl;

    @JSONField(ordinal = 5)
    private Integer seedTypeId;

    @JSONField(ordinal = 6)
    private Boolean isForNew;

}
