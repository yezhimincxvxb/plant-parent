package com.moguying.plant.core.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_mall_car
 *
 * @author
 */
@Data
@TableName("plant_mall_car")
public class MallCar implements Serializable {

    private static final long serialVersionUID = 7922407035451436638L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField
    private Integer userId;

    /**
     * 商品id
     */
    @TableField
    private Integer productId;

    /**
     * 购物车中的数量
     */
    @TableField
    private Integer productCount;

    /**
     * 是否勾选
     */
    @TableField
    private Byte isCheck;
}