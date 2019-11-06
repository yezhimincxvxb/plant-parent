package com.moguying.plant.core.entity.seed;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("plant_seed_group")
public class SeedGroup {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String groupName;
}
