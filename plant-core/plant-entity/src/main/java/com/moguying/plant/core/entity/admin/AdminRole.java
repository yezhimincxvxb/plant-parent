package com.moguying.plant.core.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@TableName("plant_admin_role")
@Data
public class AdminRole implements Serializable {

    private static final long serialVersionUID = -2226566382113511485L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    //角色名
    @TableField
    @NotNull(message = "角色名不能为空")
    private String roleName;

    //权限列表
    @TableField
    @NotNull(message = "权限列表不能为空")
    private String actionCode;

    //视图列表
    @TableField
    @NotNull(message = "视图列表不能为空")
    private String viewCode;

    //数据范围[1全部，2部门，3跟进]
    @TableField
    @NotNull(message = "数据范围不能为空")
    @Range(min = 1,max = 3,message = "只能填最小为1，最大为3的整数")
    private Integer dataRange;


    @TableField(exist = false)
    private List<AdminMenu> tree;


    @TableField(exist = false)
    private List<Integer> viewIds;


}