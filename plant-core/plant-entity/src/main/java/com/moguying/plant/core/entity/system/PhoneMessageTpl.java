package com.moguying.plant.core.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("plant_phone_message_tpl")
public class PhoneMessageTpl {

    @TableField
    private String content;

    @TableId(type = IdType.NONE)
    private String code;

    @TableField
    @TableLogic
    private Boolean isDelete;
}
