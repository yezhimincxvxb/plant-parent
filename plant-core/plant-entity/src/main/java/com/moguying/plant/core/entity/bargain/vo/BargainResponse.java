package com.moguying.plant.core.entity.bargain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BargainResponse {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 用户名
     */
    @JSONField(ordinal = 2)
    private String phone;

    /**
     * 商品id
     */
    @JSONField(ordinal = 3)
    private Integer productId;

    /**
     * 商品名称
     */
    @JSONField(ordinal = 4)
    private String productName;

    /**
     * 商品数量
     */
    @JSONField(ordinal = 5)
    private Integer productCount;

    /**
     * 商品备注
     */
    @JSONField(ordinal = 6)
    private String productInfo;

    /**
     * 产品价值
     */
    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productPrice;

    /**
     * 图片路径
     */
    @JSONField(ordinal = 8)
    private String picUrl;

    /**
     * 送出数量
     */
    @JSONField(ordinal = 9)
    private Integer sendNumber;

    /**
     * 已砍价格
     */
    @JSONField(ordinal = 10)
    private BigDecimal bargainAmount;

    /**
     * 剩余价格
     */
    @JSONField(ordinal = 11)
    private BigDecimal leftAmount;

    /**
     * 是否限量
     */
    @JSONField(ordinal = 12)
    private Integer isLimit;

    /**
     * 剩余数量
     */
    @JSONField(ordinal = 13)
    private Integer leftNumber;

    /**
     * 总数数量
     */
    @JSONField(ordinal = 14)
    private Integer totalNumber;

    /**
     * 备注
     */
    @JSONField(ordinal = 15)
    private String message;

    /**
     * 开始时间
     */
    @JSONField(ordinal = 16)
    private String beginTime;

    /**
     * 结束时间
     */
    @JSONField(ordinal = 17)
    private String endTime;

    /**
     * 百分比
     */
    @JSONField(ordinal = 18, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal rate;

    public Integer getLeftNumber() {
        if (this.isLimit != null && this.isLimit == 0)
            this.leftNumber = null;
        return leftNumber;
    }

    public Integer getTotalNumber() {
        if (this.isLimit != null && this.isLimit == 0)
            this.totalNumber = null;
        return totalNumber;
    }
}
