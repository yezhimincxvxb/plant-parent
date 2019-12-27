package com.moguying.plant.core.entity.bargain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
    @TableId
    private Integer productId;


    /**
     * 砍成人数
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer bargainCount;


    /**
     * 砍成送出数量
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer bargainNumber;


    /**
     * 是否限量商品
     */
    @JSONField(ordinal = 4)
    @TableField
    private Boolean isLimit;


    /**
     * 商品限制数量
     */
    @JSONField(ordinal = 5)
    @TableField
    private Integer bargainLimit;


    /**
     * 本人砍价系数
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer ownRate;


    /**
     * 新用户系数
     */
    @JSONField(ordinal = 7)
    @TableField
    private Integer newRate;


    /**
     * 老用户系数
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer oldRate;

    /**
     * 推送时间
     */
    @JSONField(ordinal = 9)
    @TableField
    private Date addTime;
}


