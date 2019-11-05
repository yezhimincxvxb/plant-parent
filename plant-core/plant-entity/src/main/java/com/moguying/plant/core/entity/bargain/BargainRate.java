package com.moguying.plant.core.entity.bargain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plant_bargain_rate")
public class BargainRate implements Serializable {

    private static final long serialVersionUID = 967631774403227989L;

    /**
     * 商品id
     */
    @JSONField(ordinal = 1)
    private Integer productId;

    /**
     * 本人砍价系数
     */
    @JSONField(ordinal = 2)
    private BigDecimal ownRate;

    /**
     * 新用户系数
     */
    @JSONField(ordinal = 3)
    private BigDecimal newRate;

    /**
     * 老用户系数
     */
    @JSONField(ordinal = 4)
    private BigDecimal oldRate;
}

