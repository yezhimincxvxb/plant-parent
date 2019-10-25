package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_seed_content")
@Data
public class SeedContent implements Serializable {

    private static final long serialVersionUID = -7526938626846736282L;

    @JSONField(serialize = false)
    @TableId
    private Integer seedType;

    @TableField
    private String seedIntroduce;

    @TableField
    private String contractContent;
}