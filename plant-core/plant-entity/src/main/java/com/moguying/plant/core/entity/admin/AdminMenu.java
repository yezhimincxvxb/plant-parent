package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("plant_admin_menu")
public class AdminMenu implements Serializable {

    private static final long serialVersionUID = -7378751157375060394L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父类id
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer parentId;

    /**
     * 是否常显
     */
    @JSONField(ordinal = 3)
    @TableField
    private Boolean alwaysShow;

    /**
     * 路由组件
     */
    @JSONField(ordinal = 4)
    @TableField
    private String component;

    /**
     * 路由路径
     */
    @JSONField(ordinal = 5)
    @TableField
    private String path;

    /**
     * 重定向路径
     */
    @JSONField(ordinal = 6)
    @TableField
    private String redirect;

    /**
     * 是否隐藏
     */
    @JSONField(ordinal = 6)
    @TableField
    private Boolean hidden;


    @JSONField(ordinal = 7)
    @TableField(exist = false)
    private AdminMenuMeta meta;

    /**
     * 子菜单
     */
    @JSONField(ordinal = 8)
    @TableField(exist = false)
    private List<AdminMenu> children;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<Integer> ids;

    @JSONField(ordinal = 9)
    @TableField
    private String name;


    @TableField(exist = false)
    private String parentName;

}