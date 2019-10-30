package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user_address
 * @author 
 */
@TableName("plant_user_address")
@Data
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 625780986065033763L;

    @JSONField(deserialize = false,ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JSONField(deserialize = false,ordinal = 2)
    @TableField
    private Integer userId;

    /**
     * 省份id
     */
    @JSONField(ordinal = 3)
    @TableField
    private String provinceName;

    /**
     * 市id
     */
    @JSONField(ordinal = 4)
    @TableField
    private String cityName;

    /**
     * 县id
     */
    @JSONField(ordinal = 5)
    @TableField
    private String townName;

    /**
     * 收货人名
     */
    @JSONField(ordinal = 6)
    @TableField
    private String receiveUserName;

    /**
     * 收货人手机号
     */
    @JSONField(ordinal = 7)
    @TableField
    private String receivePhone;

    /**
     * 地址详情
     */
    @JSONField(ordinal = 8)
    @TableField
    private String detailAddress;

    @JSONField(ordinal = 9,format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    @JSONField(name = "default",ordinal = 10)
    @TableField
    private Boolean isDefault;

    @JSONField(name = "isDelete",ordinal = 11)
    @TableField
    private Boolean isDelete;
}