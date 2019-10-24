package com.moguying.plant.core.entity.farmer;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_farmer_notice
 * @author 
 */
@Data
public class FarmerNotice implements Serializable {

    private Integer id;

    /**
     * 用户id
     */
    private transient Integer userId;

    /**
     * 消息内容
     */
    private String message;

    /**
     * [0未读，1已读]
     */
    private Integer state;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

}