package com.moguying.plant.core.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("plant_admin_action")
public class AdminAction implements Serializable {

    private static final long serialVersionUID = 5239427220484279173L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String actionController;

    @TableField
    private String actionMethod;

    @TableField
    private String actionDesc;

}
