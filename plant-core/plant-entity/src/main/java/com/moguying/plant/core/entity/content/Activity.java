package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_activity")
@Data
public class Activity implements Serializable {

    private static final long serialVersionUID = -5401481462499106230L;
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主标题
     */
    @JSONField(ordinal = 2)
    @TableField
    private String title;

    /**
     * 副标题
     */
    @JSONField(ordinal = 3)
    @TableField
    private String subTitle;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 4)
    @TableField
    private Date openTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 5)
    @TableField
    private Date closeTime;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 6)
    @TableField
    private String picUrl;

    /**
     * 活动内容
     */
    @JSONField(ordinal = 7)
    @TableField
    private String content;


    @JSONField(ordinal = 8)
    @TableField
    private String linkUrl;

    //辅助状态字段
    @JSONField(ordinal = 9)
    @TableField(exist = false)
    private Integer state;
}