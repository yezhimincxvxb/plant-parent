package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_adv_type")
@Data
public class AdvType implements Serializable {

    private static final long serialVersionUID = -1057942780260467947L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 位置名称
     */
    @JSONField(name = "type_name")
    @TableField
    private String typeName;

    /**
     * 描述
     */

    @TableField
    private String description;

    /**
     * 位置代号
     */
    @TableField
    @JSONField(name = "position_flag")
    private String positionFlag;

}