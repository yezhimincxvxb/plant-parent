package com.moguying.plant.core.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_user_message_tpl
 *
 * @author
 */
@TableName("plant_user_message_tpl")
@Data
public class UserMessageTpl implements Serializable {
    private static final long serialVersionUID = -5989123744034073741L;
    /**
     * 模版方法值
     */
    @TableId
    private String actionCode;

    @TableField
    private String messageTitle;

    /**
     * 消息模板
     */
    @TableField
    private String messageTpl;

    @TableField
    private Boolean isOpen;

}