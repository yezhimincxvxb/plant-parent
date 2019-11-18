package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_banner")
@Data
public class Banner implements Serializable {


    private static final long serialVersionUID = -6549861352706401211L;
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * banner类型[1移动端,2PC端,3移动商城,4引导页,5开屏页,6广告页,7保险范本]
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer type;

    /**
     * banner名
     */
    @JSONField(ordinal = 3)
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * banner图片
     */
    @JSONField(ordinal = 4)
    @TableField
    private String picUrl;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 5)
    @TableField
    private String thumbPicUrl;

    /**
     * banner跳转地址
     */
    @JSONField(ordinal = 6)
    @TableField
    private String jumpUrl;

    /**
     * 是否显示[0不显示，1显示]
     */
    @JSONField(ordinal = 7, name = "isShow")
    @TableField
    private Boolean isShow;

    /**
     * 排序值
     */
    @JSONField(ordinal = 8)
    @TableField
    private Byte sortOrder;

    /**
     * 显示时间
     */
    @JSONField(ordinal = 9, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date showTime;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;


}