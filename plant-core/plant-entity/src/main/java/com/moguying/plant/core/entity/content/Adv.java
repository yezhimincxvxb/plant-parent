package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_adv")
@Data
public class Adv implements Serializable {

    private static final long serialVersionUID = -8553992270148905801L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 广告位置[类型]
     */
    @JSONField(name = "type_id")
    @TableField
    private Integer typeId;

    /**
     * 广告描述
     */
    @TableField
    private String name;

    /**
     * 图片url
     */
    @JSONField(name = "pic_url")
    @TableField
    private String picUrl;

    /**
     * 缩略图url
     */
    @JSONField(name = "thumb_pic_url")
    @TableField
    private String thumbPicUrl;

    /**
     * 是否显示
     */
    @JSONField(name = "is_show")
    @TableField
    private Boolean isShow;

    @JSONField(name = "order_number")
    @TableField
    private Integer orderNumber;

    /**
     * 广告跳转地址
     */
    @JSONField(name = "adv_url")
    @TableField
    private String advUrl;

    /**
     * 开始时间
     */
    @JSONField(name = "start_time")
    @TableField
    private Date startTime;

    /**
     * 结束时间
     */
    @JSONField(name = "close_time")
    @TableField
    private Date closeTime;

}