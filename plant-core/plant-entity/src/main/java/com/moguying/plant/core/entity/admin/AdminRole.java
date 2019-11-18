package com.moguying.plant.core.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName("plant_admin_role")
@Data
public class AdminRole implements Serializable {

    private static final long serialVersionUID = -2226566382113511485L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String roleName;

    @TableField(exist = false)
    private List<Integer> actionIds;

    @TableField
    private String actionCode;

    @TableField(exist = false)
    private List<AdminMenu> tree;


}