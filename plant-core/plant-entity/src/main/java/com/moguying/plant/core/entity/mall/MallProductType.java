package com.moguying.plant.core.entity.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("plant_mall_product_type")
public class MallProductType implements Serializable {

    private static final long serialVersionUID = 5367086899627820318L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    @TableField
    private String typeName;

    /**
     * 分类排序
     */
    @TableField
    private Integer typeSort;

}