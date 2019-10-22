package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_admin_menu_meta
 * @author Qinhir
 */
@Data
public class AdminMenuMeta implements Serializable {
    /**
     * 菜单id
     */
    @JSONField(serialize = false)
    private Integer menuId;

    /**
     * 菜单名称
     */
    @JSONField(ordinal = 1)
    private String title;

    /**
     * 菜单图标
     */
    @JSONField(ordinal = 2)
    private String icon;

    /**
     * 是否缓存
     */
    @JSONField(ordinal = 3)
    private Boolean noCache;
}