package com.moguying.plant.core.entity.coin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户-蘑菇币类
 */
@TableName("plant_sale_coin")
@Data
public class SaleCoin implements Serializable {

    private static final long serialVersionUID = 59887896147619857L;

    /**
     * 用户ID
     */
    @TableId
    @JSONField(ordinal = 1)
    private Integer userId;

    /**
     * 蘑菇币
     */
    @TableField
    @JSONField(ordinal = 2)
    private Integer coinCount;
}
