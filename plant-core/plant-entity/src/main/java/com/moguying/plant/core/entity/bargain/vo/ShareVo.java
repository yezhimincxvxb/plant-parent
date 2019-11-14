package com.moguying.plant.core.entity.bargain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShareVo {

    /**
     * 订单id
     */
    @JSONField(ordinal = 1)
    private Integer orderId;

    /**
     * 用户id
     */
    @JSONField(ordinal = 2)
    private Integer userId;

    /**
     * 砍价口令
     */
    @JSONField(ordinal = 3)
    private String symbol;
}
