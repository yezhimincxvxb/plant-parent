package com.moguying.plant.core.entity.reap;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("plant_reap_exc_log")
public class ReapExcLog {

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JSONField(ordinal = 2)
    @TableField
    private Integer userId;

    @JSONField(ordinal = 3)
    @TableField
    private Integer productId;

    @JSONField(ordinal = 4)
    @TableField
    private Integer reapId;

    @JSONField(ordinal = 5,serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal excWeigh;

    @JSONField(ordinal = 6,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    @JSONField(ordinal = 7)
    @TableField
    private Integer excCount;

    @JSONField(ordinal = 8)
    @TableField(exist = false)
    private String productName;
}
