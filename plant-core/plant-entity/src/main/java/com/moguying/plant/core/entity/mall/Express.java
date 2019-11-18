package com.moguying.plant.core.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_express
 *
 * @author
 */
@Data
@TableName("plant_express")
public class Express implements Serializable {

    private static final long serialVersionUID = 1260101232098232660L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 收货地址id
     */
    @TableField
    private Integer addressId;

    /**
     * 产品id
     */
    @TableField
    private Integer productId;

    /**
     * 产品类型[1菌包兑换，2采摘兑换，3商城商品]
     */
    @TableField
    private Integer productType;

    /**
     * 快递单号
     */
    @TableField
    private String expressNumber;

}