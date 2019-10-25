package com.moguying.plant.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_phone_message")
@Data
public class PhoneMessage implements Serializable {
    private static final long serialVersionUID = 8269830185975289046L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String phone;

    @TableField
    private String code;

    @TableField
    private Integer state;

    @TableField
    private String message;

    @TableField
    private Date addTime;

}