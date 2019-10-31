package com.moguying.plant.core.entity.taste;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Taste {

    @JSONField(ordinal = 1)
    private String id;

    /**
     * 产品id
     */
    @JSONField(ordinal = 2)
    private Integer productId;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 3)
    private String thumbPic;


    /**
     * 图
     */
    @JSONField(ordinal = 4)
    private String pic;


    /**
     * 试吃份数
     */
    @JSONField(ordinal = 5)
    private Integer tasteCount;


    /**
     * 开始时间
     */
    @JSONField(ordinal = 6,format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;


    /**
     * 结束时间
     */
    @JSONField(ordinal = 7,format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    /**
     * 状态
     */
    @JSONField(ordinal = 8)
    private Integer state;


    /**
     * 是否上线
     */
    @JSONField(ordinal = 9)
    private Boolean isShow;

    /**
     * 用户是否申请
     */
    @JSONField(ordinal = 10)
    private Boolean hasApply;

    /**
     * 申请人数
     */
    @JSONField(ordinal = 11)
    private long applyCount;

}
