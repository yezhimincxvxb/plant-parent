package com.moguying.plant.core.entity.system.vo;

import com.moguying.plant.core.entity.admin.AdminUser;
import lombok.Data;

@Data
public class SessionAdminUser {

    public static final String sessionKey = "admin_user";

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 用户角色id
     */
    private Integer roleId;


    /**
     * 部门id
     */
    private Integer deptId;

    public SessionAdminUser(AdminUser adminUser) {
        id = adminUser.getId();
        realName = adminUser.getRealName();
        isLocked = adminUser.getIsLocked();
        roleId = adminUser.getRoleId();
        deptId = adminUser.getDeptId();
    }
}
