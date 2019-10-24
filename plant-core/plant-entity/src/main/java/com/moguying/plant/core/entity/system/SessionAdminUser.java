package com.moguying.plant.core.entity.system;

import com.moguying.plant.core.entity.admin.AdminUser;

public class SessionAdminUser {

    public static final String sessionKey = "admin_user";

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 用户角色id
     */
    private Integer roleId;

    public SessionAdminUser (AdminUser adminUser){
        id = adminUser.getId();
        name = adminUser.getUserName();
        isLocked = adminUser.getIsLocked();
        roleId = adminUser.getRoleId();
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SessionAdminUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isLocked=" + isLocked +
                ", roleId=" + roleId +
                '}';
    }
}
