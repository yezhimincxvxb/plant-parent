package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_adv_type
 * @author 
 */
@Data
public class AdvType implements Serializable {
    private Integer id;

    /**
     * 位置名称
     */
    @JSONField(name = "type_name")
    private String typeName;

    /**
     * 描述
     */
    private String description;

    /**
     * 位置代号
     */
    @JSONField(name = "position_flag")
    private String positionFlag;

    private static final long serialVersionUID = 1L;

}