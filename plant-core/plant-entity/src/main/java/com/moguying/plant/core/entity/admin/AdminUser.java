package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("plant_admin_user")
@Data
public class AdminUser implements Serializable {


    private static final long serialVersionUID = 8148163634042409486L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 后台用户名
     */
    @JSONField
    @TableField
    private String realName;


    /**
     * 手机号
     */
    @JSONField
    @TableField
    private String phone;


    /**
     * 是否锁定
     */
    @TableField
    private Boolean isLocked;

    /**
     * 最后一次登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date lastLoginTime;

    /**
     * 最后一次登录ip
     */
    @TableField
    private String lastLoginIp;

    /**
     * 用户角色id
     */
    @TableField
    private Integer roleId;


    /**
     * 部门id
     */
    @TableField
    private Integer deptId;


    @TableField
    private Integer bindId;


    /**
     * 图形验证码
     */
    @TableField(exist = false)
    private String code;

    /**
     * 是否新信息
     */
    @TableField(exist = false)
    private Boolean hasNewMessage;

    /**
     * 对应角色
     */
    @TableField(exist = false)
    private AdminRole role;

    /**
     * 跳由表
     */
    @TableField(exist = false)
    private List<AdminMenu> routers;

}