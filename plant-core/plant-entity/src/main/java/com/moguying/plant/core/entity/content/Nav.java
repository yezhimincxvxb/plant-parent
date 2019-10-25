package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("plant_nav")
@Data
public class Nav implements Serializable {

    private static final long serialVersionUID = 1916870269934257733L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String name;

    @JSONField
    @TableField
    private String jumpUrl;

    /**
     * 是否显示[0否，1是]
     */
    @JSONField
    @TableField
    private Boolean isShow;

    /**
     * 是否新窗口打开[0否，1是]
     */
    @JSONField
    @TableField
    private Boolean isBlank;

    @JSONField
    @TableField
    private Integer orderNumber;

    /**
     * 类型[1种植平台，2商城平台]
     */
    @TableField
    private Integer type;

}