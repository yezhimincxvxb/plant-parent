package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * plant_user_inner
 * @author 
 */
public class UserInner implements Serializable {
    private Integer id;

    @JSONField(name = "name")
    private String userName;

    @JSONField(name = "phone")
    private String userPhone;

    /**
     * 辅助字段
     * 内购份数
     */
    @JSONField(name = "count")
    private Integer innerCount;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getInnerCount() {
        return innerCount;
    }

    public void setInnerCount(Integer innerCount) {
        this.innerCount = innerCount;
    }
}