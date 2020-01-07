package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("plant_article_help")
public class ArticleHelp implements Serializable {

    private static final long serialVersionUID = 124586425600182158L;

    @JSONField(serialize = false)
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JSONField(serialize = false)
    @TableField
    private Integer typeId;

    @JSONField(serialize = false)
    @TableField
    private String title;

    @JSONField(ordinal = 1)
    @TableField
    private String question;

    @JSONField(ordinal = 2)
    @TableField
    private String answer;

    @JSONField(ordinal = 3)
    @TableField
    private Boolean isShow;

    @JSONField(ordinal = 4, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    @JSONField(ordinal = 5, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date updateTime;

    // 辅助字段
    @TableField(exist = false)
    @JSONField(serialize = false)
    private Date start;
    @TableField(exist = false)
    @JSONField(serialize = false)
    private Date end;

}