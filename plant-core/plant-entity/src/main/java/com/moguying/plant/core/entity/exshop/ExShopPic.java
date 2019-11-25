package com.moguying.plant.core.entity.exshop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_ex_shop_pic
 * @author qinhir
 */
@Data
@TableName("plant_ex_shop_pic")
public class ExShopPic implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 体验店id
     */
    @TableField
    private Integer shopId;

    /**
     * 图片地址
     */
    @TableField
    private String picUrl;

    /**
     * 缩略图
     */
    @TableField
    private String thumbPicUrl;

}