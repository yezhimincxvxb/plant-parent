package com.moguying.plant.core.entity.mall;

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

@TableName("plant_mall_product")
@Data
public class MallProduct implements Serializable {

    private static final long serialVersionUID = -1145586995248505240L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 图片地址
     */
    @JSONField(ordinal = 2)
    @TableField
    private String picUrl;

    /**
     * 缩略图地址
     */
    @JSONField(ordinal = 3)
    @TableField
    private String thumbPicUrl;

    /**
     * 产品名称
     */
    @JSONField(ordinal = 4)
    @TableField
    private String name;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    @TableField
    private BigDecimal price;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 6)
    @TableField
    private BigDecimal oldPrice;

    /**
     * 快递费
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    @TableField
    private BigDecimal fee;

    /**
     * 总库存
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer totalCount;

    /**
     * 库存
     */
    @JSONField(ordinal = 9)
    @TableField
    private Integer leftCount;

    /**
     * 购买份数
     */
    @JSONField(ordinal = 10)
    @TableField
    private Integer hasCount;

    /**
     * 商品摘要
     */
    @JSONField(ordinal = 11)
    @TableField
    private String summaryDesc;

    /**
     * 是否上架[0否，1是]
     */
    @JSONField(ordinal = 12)
    @TableField
    private Boolean isShow;

    /**
     * 配送信息
     */
    @JSONField(ordinal = 13)
    @TableField
    private String deliveryInfo;

    /**
     * 商品详情
     */
    @JSONField(ordinal = 14)
    @TableField
    private String productDesc;

    @JSONField(ordinal = 15,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 一份所需消耗的蘑菇币
     */
    @JSONField(ordinal = 16)
    @TableField
    private Integer consumeCoins;

    /**
     * 砍价份数
     */
    @JSONField(ordinal = 17)
    @TableField
    private Integer bargainNumber;

    /**
     * 需砍刀数
     */
    @JSONField(ordinal = 18)
    @TableField
    private Integer bargainCount;

    /**
     * 是否限量
     */
    @JSONField(ordinal = 19)
    @TableField
    private Boolean isLimit;

    /**
     * 限量数量
     */
    @JSONField(ordinal = 20)
    @TableField
    private Integer bargainLimit;


    /**
     * 商品类型id
     */
    @JSONField(serialize = false)
    private Integer typeId;


    @JSONField(ordinal = 17)
    private String typeName;


    @JSONField(ordinal = 18)
    private Boolean isHot;

}