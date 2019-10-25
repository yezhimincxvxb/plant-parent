package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("plant_farmer_sign")
public class FarmerSign implements Serializable {
    private static final long serialVersionUID = 437824646486058482L;

    @TableId
    private Integer userId;

    @TableField
    private byte[] signCount;
}