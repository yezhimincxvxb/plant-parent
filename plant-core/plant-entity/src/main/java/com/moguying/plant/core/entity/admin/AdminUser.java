package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * plant_admin_user
 * @author 
 */
@Data
public class AdminUser implements Serializable {
    private Integer id;

    /**
     * 后台用户名
     */
    @JSONField(name = "username")
    private String userName;

    /**
     * 登录密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 最后一次登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 最后一次登录ip
     */
    private String lastLoginIp;

    /**
     * 用户角色id
     */
    private Integer roleId;


    /**
     * 图形验证码
     */
    private String code;

    private Integer bindId;

    /**
     * 前台用户手机号
     */
    private String  phone;

    private Boolean hasNewMessage;

    private AdminRole role;

    /**
     * 跳由表
     */
    private List<AdminMenu> routers;

}