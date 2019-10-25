package com.moguying.plant.core.entity.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("plant_admin_menu_meta")
public class AdminMenuMeta implements Serializable {
    private static final long serialVersionUID = -3782218415121916378L;
    /**
     * 菜单id
     */
    @JSONField(serialize = false)
    @TableId
    private Integer menuId;

    /**
     * 菜单名称
     */
    @JSONField(ordinal = 1)
    @TableField
    private String title;

    /**
     * 菜单图标
     */
    @JSONField(ordinal = 2)
    @TableField
    private String icon;

    /**
     * 是否缓存
     */
    @JSONField(ordinal = 3)
    @TableField
    private Boolean noCache;
}