package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserFertilizerInfo {

    /**
     * 用户使用的券id
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 券id
     */
    @JSONField(ordinal = 2)
    private Integer fertilizerId;

    /**
     * 券类型[1-优卖券、2-满减券、3-种植券]
     */
    @JSONField(ordinal = 3)
    private Integer typeId;

    /**
     * 券类型名称[优卖券、满减券、种植券]
     */
    @JSONField(ordinal = 4)
    private String typeName;

    /**
     * 券金额
     */
    @JSONField(ordinal = 5,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fertilizerAmount;

    /**
     * 券满足使用的最小金额
     */
    @JSONField(ordinal = 6,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMin;

    /**
     * 券满足使用的最大金额
     */
    @JSONField(ordinal = 7,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMax;

    /**
     * 券开始使用时间
     */
    @JSONField(ordinal = 8,format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 券结束使用时间
     */
    @JSONField(ordinal = 9 ,format = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 券的使用状态[0未使用，1已使用，2已过期]
     */
    @JSONField(ordinal = 10)
    private Integer state;

    /**
     * 是否在单个大棚使用
     */
    @JSONField(ordinal = 11)
    private Boolean userInSingleBlock;

    /**
     * 指定棚区的大棚编号
     */
    @JSONField(ordinal = 12)
    private String useInBlockNumber;

    /**
     * 是否在单商品中使用
     */
    @JSONField(ordinal = 13)
    private Boolean useInSingleProduct;

    /**
     * 指定商品名称
     */
    @JSONField(ordinal = 14)
    private String useInProductName;

    /**
     * 是否在单菌包中使用
     */
    @JSONField(ordinal = 15)
    private Boolean useInSingleSeed;

    /**
     * 指定菌包名称
     */
    @JSONField(ordinal = 16)
    private String useInSeedTypeName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserFertilizerInfo){
            return this.getId().equals(((UserFertilizerInfo) obj).getId());
        }
        return false;
    }
}

