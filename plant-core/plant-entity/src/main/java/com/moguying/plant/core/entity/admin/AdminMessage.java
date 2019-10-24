package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_admin_message
 * @author 
 */
@Data
public class AdminMessage implements Serializable {
    private Integer id;

    /**
     * 后台用户id
     */
    private Integer userId;

    /**
     * 信息
     */
    private String message;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 0未审核，1已审核，2已读
     */
    private Integer state;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
}