package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.annotation.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "真实姓名不能为空")
    private String realName;


    /**
     * 手机号
     */
    @JSONField
    @TableField
    @IsMobile
    private String phone;


    /**
     * 是否锁定
     */
    @TableField
    @NotNull(message = "用户状态不能为空")
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
    @NotNull(message = "用户角色不能为空")
    private Integer roleId;


    /**
     * 部门id
     */
    @TableField
    @NotNull(message = "用户部门不能为空")
    private Integer deptId;


    @TableField
    private Integer bindId;


    /**
     * 渠道商绑定的手机号
     */
    @TableField(exist = false)
    private String bindPhone;

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