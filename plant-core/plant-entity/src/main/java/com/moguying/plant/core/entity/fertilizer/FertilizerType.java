package com.moguying.plant.core.entity.fertilizer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_fertilizer_type")
@Data
public class FertilizerType implements Serializable {
    private static final long serialVersionUID = -7742079993642242407L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 肥料类型
     */
    @TableField
    private String typeName;


}