package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user_message
 * @author 
 */
public class UserMessage implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String title;
    /**
     * 消息内容
     */
    @JSONField(ordinal = 3)
    private String message;

    /**
     * 用户id
     */
    @JSONField(ordinal = 4)
    private Integer userId;


    /**
     * 用户手机号
     */
    @JSONField(ordinal = 5)
    private String phone;

    /**
     * 是否已读[0未读，1已读]
     */
    @JSONField(ordinal = 6)
    private Boolean isRead;

    /**
     * 是否删除[0未删除，1已删除]
     */
    @JSONField(ordinal = 7)
    private Boolean isDelete;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy.MM.dd HH:mm",ordinal = 8)
    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}