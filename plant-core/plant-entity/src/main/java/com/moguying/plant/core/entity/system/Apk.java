package com.moguying.plant.core.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_apk")
@Data
public class Apk implements Serializable {

    private static final long serialVersionUID = -1229614742460678866L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 版本号
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer version;

    /**
     * 下载链接
     */
    @JSONField(ordinal = 3)
    @TableField
    private String downloadUrl;

    /**
     * 是否上架[0否，1是]
     */
    @JSONField(ordinal = 4)
    @TableField
    private Boolean isShow;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 5)
    @TableField
    private Date addTime;

    @JSONField(ordinal = 6)
    @TableField
    private String versionName;

    @JSONField(ordinal = 7)
    @TableField
    private String updateDesc;

}