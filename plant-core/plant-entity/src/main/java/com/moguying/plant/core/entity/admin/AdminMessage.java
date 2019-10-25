package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("plant_admin_message")
public class AdminMessage implements Serializable {

    private static final long serialVersionUID = -4500558486759874900L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 后台用户id
     */
    @TableField
    private Integer userId;

    /**
     * 信息
     */
    @TableField
    private String message;

    /**
     * 下载地址
     */
    @TableField
    private String downloadUrl;

    /**
     * 0未审核，1已审核，2已读
     */
    @TableField
    private Integer state;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;
}