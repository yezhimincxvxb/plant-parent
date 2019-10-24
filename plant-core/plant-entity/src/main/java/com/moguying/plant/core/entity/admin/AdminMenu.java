package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * plant_admin_menu
 * @author Qinhir
 */
@Data
public class AdminMenu implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 父类id
     */
    @JSONField(ordinal = 2)
    private Integer parentId;

    /**
     * 是否常显
     */
    @JSONField(ordinal = 3)
    private Boolean alwaysShow;

    /**
     * 路由组件
     */
    @JSONField(ordinal = 4)
    private String component;

    /**
     * 路由路径
     */
    @JSONField(ordinal = 5)
    private String path;

    /**
     * 重定向路径
     */
    @JSONField(ordinal = 6)
    private String redirect;

    /**
     * 是否隐藏
     */
    @JSONField(ordinal = 6)
    private Boolean hidden;


    @JSONField(ordinal = 7)
    private AdminMenuMeta meta;

    /**
     * 子菜单
     */
    @JSONField(ordinal = 8)
    private List<AdminMenu> children;

    @JSONField(serialize = false)
    private List<Integer> ids;

    @JSONField(ordinal = 9)
    private String name;

}