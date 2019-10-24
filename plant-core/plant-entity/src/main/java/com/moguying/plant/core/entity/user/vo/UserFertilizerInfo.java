package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserFertilizerInfo {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 券类型名称
     */
    @JSONField(ordinal = 2)
    private String typeName;

    /**
     * 券类型
     */
    @JSONField(ordinal = 3)
    private Integer typeId;


    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fertilizerAmount;


    /**
     * 有效期
     */
    @JSONField(ordinal = 7 ,format = "yyyy.MM.dd")
    private Date expireTime;


    /**
     * 在指定棚区使用
     */
    @JSONField(ordinal = 9)
    private String useInBlockNumber;


    /**
     * 在指定商品使用
     */
    @JSONField(ordinal = 10)
    private String useInProductName;


    /**
     * 券满足使用的最小金额
     */
    @JSONField(ordinal = 11,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMin;

    /**
     * 券满足使用的最大金额
     */
    @JSONField(ordinal = 12,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMax;

    /**
     * 在指定商品使用
     */
    @JSONField(ordinal = 13)
    private String useInSeedTypeName;

    @JSONField(ordinal = 14,format = "yyyy.MM.dd")
    private Date startTime;

    @JSONField(ordinal = 15)
    private Integer state;

    /**
     * 是否在单商品中使用
     */
    @JSONField(ordinal = 16)
    private Boolean useInSingleProduct;

    /**
     * 是否在单菌包中使用
     */
    @JSONField(ordinal = 17)
    private Boolean useInSingleSeed;

    @JSONField(ordinal = 18)
    private Integer fertilizerId;

    /**
     * 是否在单个大棚使用
     */
    @JSONField(ordinal = 19)
    private Boolean userInSingleBlock;


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserFertilizerInfo){
            return this.getId().equals(((UserFertilizerInfo) obj).getId());
        }
        return false;
    }
}

