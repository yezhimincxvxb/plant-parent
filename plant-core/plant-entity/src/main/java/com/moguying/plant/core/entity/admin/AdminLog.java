package com.moguying.plant.core.entity.admin;

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
    private Integer id;
    /**
     * 后台用户id
     */
    @TableField
    private Integer userId;
    /**
     * 操作代码
     */
    @TableField
    private String actionCode;
    /**
     * 操作参数
     */
    @TableField
    private String actionParam;
    /**
     * 操作时间
     */
    @TableField
    private Date addTime;

}
