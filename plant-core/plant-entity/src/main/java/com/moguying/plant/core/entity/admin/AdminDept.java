package com.moguying.plant.core.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName("plant_admin_dept")
public class AdminDept {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    @NotNull(message = "部门名称不能为空")
    private String deptName;
}
