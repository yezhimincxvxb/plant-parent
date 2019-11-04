package com.moguying.plant.core.entity.bargain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("plant_bargain_log")
public class BargainLog implements Serializable {

    private static final long serialVersionUID = 2324954044983949274L;

    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 用户id
     */
    @TableField
    @JSONField(ordinal = 2)
    private Integer userId;

    /**
     * 分享者id
     */
    @TableField
    @JSONField(ordinal = 3)
    private Integer shareId;

    /**
     * 产品id
     */
    @TableField
    @JSONField(ordinal = 4)
    private Integer productId;

    /**
     * 详情id
     */
    @TableField
    @JSONField(ordinal = 5)
    private Integer detailId;

    /**
     * 帮砍价格
     */
    @TableField
    @JSONField(ordinal = 6, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal helpAmount;

    /**
     * 备注
     */
    @TableField
    @JSONField(ordinal = 7)
    private String message;

    /**
     * 帮砍时间
     */
    @TableField
    @JSONField(ordinal = 8, format = "yyyy-MM-dd HH:mm:ss")
    private Date helpTime;
}
