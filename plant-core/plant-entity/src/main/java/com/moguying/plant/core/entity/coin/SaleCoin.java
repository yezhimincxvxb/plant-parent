package com.moguying.plant.core.entity.coin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-蘑菇币类
 */
@Data
public class SaleCoin implements Serializable {

    private static final long serialVersionUID = 59887896147619857L;

    /**
     * 用户ID
     */
    @JSONField(ordinal = 1)
    private Integer userId;

    /**
     * 蘑菇币
     */
    @JSONField(ordinal = 2)
    private Integer coinCount;
}
