package com.moguying.plant.core.entity.farmer;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_farmer_notice")
@Data
public class FarmerNotice implements Serializable {

    private static final long serialVersionUID = 596821671891830663L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField
    private transient Integer userId;

    /**
     * 消息内容
     */
    @TableField
    private String message;

    /**
     * [0未读，1已读]
     */
    @TableField
    private Integer state;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

}