package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_farmer_level
 *
 * @author
 */
@Data
@TableName("plant_farmer_level")
public class FarmerLevel implements Serializable {
    private static final long serialVersionUID = 6248153491870934905L;

    @TableId
    private Integer level;

    @TableField
    private Integer growUpCountMin;

    @TableField
    private Integer growUpCountMax;
}