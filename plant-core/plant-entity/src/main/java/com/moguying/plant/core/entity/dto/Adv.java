package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_adv
 * @author Qinhir
 */

@Data
public class Adv implements Serializable {
    private Integer id;

    /**
     * 广告位置[类型]
     */
    @JSONField(name = "type_id")
    private Integer typeId;

    /**
     * 广告描述
     */
    private String name;

    /**
     * 图片url
     */
    @JSONField(name = "pic_url")
    private String picUrl;

    /**
     * 缩略图url
     */
    @JSONField(name = "thumb_pic_url")
    private String thumbPicUrl;

    /**
     * 是否显示
     */
    @JSONField(name = "is_show")
    private Boolean isShow;

    @JSONField(name = "order_number")
    private Integer orderNumber;

    /**
     * 广告跳转地址
     */
    @JSONField(name = "adv_url")
    private String advUrl;

    /**
     * 开始时间
     */
    @JSONField(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField(name = "close_time")
    private Date closeTime;

    private static final long serialVersionUID = 1L;

}