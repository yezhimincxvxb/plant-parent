package com.moguying.plant.core.entity.bargain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BargainVo {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 订单id
     */
    @JSONField(ordinal = 2)
    private Integer orderId;

    /**
     * 用户名
     */
    @Excel(name = "参与人手机号")
    @JSONField(ordinal = 3, serializeUsing = IdCardSerialize.class)
    private String phone;

    /**
     * 商品id
     */
    @JSONField(ordinal = 4)
    private Integer productId;

    /**
     * 商品名称
     */
    @JSONField(ordinal = 5)
    private String productName;

    /**
     * 商品数量
     */
    @JSONField(ordinal = 6)
    private Integer productCount;

    /**
     * 商品备注
     */
    @JSONField(ordinal = 7)
    private String productInfo;

    /**
     * 产品价值
     */
    @JSONField(ordinal = 8, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productPrice;

    /**
     * 图片路径
     */
    @JSONField(ordinal = 9)
    private String picUrl;

    /**
     * 送出数量
     */
    @JSONField(ordinal = 10)
    private Integer sendNumber;

    /**
     * 已砍价格
     */
    @JSONField(ordinal = 11, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal bargainAmount;

    /**
     * 剩余价格
     */
    @JSONField(ordinal = 12, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal leftAmount;

    /**
     * 是否限量
     */
    @JSONField(ordinal = 13)
    private Integer isLimit;

    /**
     * 剩余数量
     */
    @JSONField(ordinal = 14)
    private Integer leftNumber;

    /**
     * 总数数量
     */
    @JSONField(ordinal = 15)
    private Integer totalNumber;

    /**
     * 备注
     */
    @JSONField(ordinal = 16)
    private String message;

    /**
     * 百分比
     */
    @JSONField(ordinal = 17, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal rate;

    /**
     * 帮砍价格
     */
    @Excel(name = "帮砍价格")
    @JSONField(ordinal = 18, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal helpAmount;

    /**
     * 开始时间
     */
    @Excel(name = "帮砍时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 19, format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JSONField(ordinal = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 砍价口令
     */
    @JSONField(ordinal = 21)
    private String symbol;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 用户id
     */
    private Integer userId;

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
