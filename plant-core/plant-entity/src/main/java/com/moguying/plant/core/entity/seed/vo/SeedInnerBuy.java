package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.user.UserInner;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SeedInnerBuy implements Serializable {

    private static final long serialVersionUID = -2138334079236392997L;

    @JSONField(ordinal = 1)
    private Integer seedId;

    @JSONField(ordinal = 2)
    private Integer userCount;

    @JSONField(ordinal = 3)
    private Integer orderCount;

    @JSONField(ordinal = 4)
    private List<UserInner> userInners;

}
