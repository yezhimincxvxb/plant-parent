package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_activity
 * @author Qinhir
 */
@Data
public class Activity implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 主标题
     */
    @JSONField(ordinal = 2)
    private String title;

    /**
     * 副标题
     */
    @JSONField(ordinal = 3)
    private String subTitle;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 4)
    private Date openTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 5)
    private Date closeTime;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 6)
    private String picUrl;

    /**
     * 活动内容
     */
    @JSONField(ordinal = 7)
    private String content;


    @JSONField(ordinal = 8)
    private String linkUrl;

    //辅助状态字段
    @JSONField(ordinal = 9)
    private Integer state;

    private static final long serialVersionUID = 1L;
}