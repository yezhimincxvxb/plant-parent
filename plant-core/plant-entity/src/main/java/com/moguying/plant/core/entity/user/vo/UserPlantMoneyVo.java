package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
public class UserPlantMoneyVo implements Serializable {

    private static final long serialVersionUID = 8593233354679038559L;

    @JSONField(ordinal = 1)
    private Integer userId;

    @JSONField(ordinal = 2)
    private String userName;

    @JSONField(ordinal = 3, serializeUsing = IdCardSerialize.class)
    private String phone;

    /**
     * 购买总数
     */
    @JSONField(ordinal = 4)
    private Integer buyCount;

    /**
     * 已种植总数
     */
    @JSONField(ordinal = 5)
    private Integer plantCount;

    /**
     * 剩余可种植数
     */
    @JSONField(ordinal = 6)
    private Integer leftCount;

    /**
     * 总购买金额
     */
    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal buyMoney;

    /**
     * 待收金额
     */
    @JSONField(ordinal = 8, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal collectMoney;

    /**
     * 注册时间
     */
    @JSONField(ordinal = 9, format = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    public Integer getLeftCount() {
        if (Objects.nonNull(buyCount) && Objects.nonNull(plantCount)) {
            return buyCount - plantCount;
        }
        return leftCount;
    }


}
