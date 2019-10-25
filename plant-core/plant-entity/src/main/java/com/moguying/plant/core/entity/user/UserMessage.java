package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user_message
 * @author 
 */
@TableName("plant_user_message")
@Data
public class UserMessage implements Serializable {

    private static final long serialVersionUID = -1867242041225423831L;


    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JSONField(ordinal = 2)
    @TableField
    private String title;
    /**
     * 消息内容
     */
    @JSONField(ordinal = 3)
    @TableField
    private String message;

    /**
     * 用户id
     */
    @JSONField(ordinal = 4)
    @TableField
    private Integer userId;


    /**
     * 用户手机号
     */
    @JSONField(ordinal = 5)
    @TableField(exist = false)
    private String phone;

    /**
     * 是否已读[0未读，1已读]
     */
    @JSONField(ordinal = 6)
    @TableField
    private Boolean isRead;

    /**
     * 是否删除[0未删除，1已删除]
     */
    @JSONField(ordinal = 7)
    @TableField
    private Boolean isDelete;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy.MM.dd HH:mm",ordinal = 8)
    @TableField
    private Date addTime;

}
