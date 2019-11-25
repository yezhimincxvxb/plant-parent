package com.moguying.plant.core.entity.exshop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_ex_shop_content
 * @author qinhir
 */
@Data
@TableName("plant_ex_show_content")
public class ExShopContent implements Serializable {
    /**
     * 体验店id
     */
    @TableId(type = IdType.AUTO)
    private Integer shopId;

    /**
     * 体验店内容
     */
    @TableField
    private String content;

}