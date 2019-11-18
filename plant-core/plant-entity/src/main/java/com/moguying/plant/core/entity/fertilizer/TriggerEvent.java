package com.moguying.plant.core.entity.fertilizer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_trigger_event")
@Data
public class TriggerEvent implements Serializable {

    private static final long serialVersionUID = 2541840431975911619L;

    /**
     * 触发事件英文
     */
    @TableId
    private String triggerEvent;

    /**
     * 触发事件中文
     */
    @TableField
    private String mark;

}