package com.moguying.plant.core.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_apk
 * @author 
 */
@Data
public class Apk implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 版本号
     */
    @JSONField(ordinal = 2)
    private Integer version;

    /**
     * 下载链接
     */
    @JSONField(ordinal = 3)
    private String downloadUrl;

    /**
     * 是否上架[0否，1是]
     */
    @JSONField(ordinal = 4)
    private Boolean isShow;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 5)
    private Date addTime;

    @JSONField(ordinal = 6)
    private String versionName;

    @JSONField(ordinal = 7)
    private String updateDesc;

}