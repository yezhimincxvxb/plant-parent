package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_admin_log")
@Data
public class AdminLog implements Serializable {
    private static final long serialVersionUID = -3337737514487067965L;

    @TableId(type = IdType.AUTO)
    @JSONField(ordinal = 1)
    private Integer id;
    /**
     * 后台用户id
     */
    @TableField
    @JSONField(ordinal = 2)
    private Integer userId;
    /**
     * 操作代码
     */
    @TableField
    @JSONField(ordinal = 3)
    private String actionCode;
    /**
     * 操作参数
     */
    @TableField
    @JSONField(ordinal = 4)
    private String actionParam;

    /**
     * 请求方法
     */
    @TableField
    @JSONField(ordinal = 5)
    private String actionMethod;


    /**
     * 操作描述
     */
    @TableField
    @JSONField(ordinal = 6)
    private String actionDesc;


    /**
     * 操作时间
     */
    @TableField
    @JSONField(ordinal = 7, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    // 辅助字段
    private Date start;
    private Date end;

}
