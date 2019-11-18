package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("plant_article")
public class Article implements Serializable {
    private static final long serialVersionUID = -6553492196212016336L;


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    @TableField
    private String title;

    /**
     * 文章分类
     */
    @TableField
    private Integer typeId;

    @TableField(exist = false)
    private String typeName;

    @TableField(exist = false)
    private String typeUrlName;


    /**
     * seo关键字
     */
    @TableField
    private String seoKey;

    /**
     * 发布时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date publishTime;

    /**
     * 是否显示[0否，1是]
     */
    @JSONField
    @TableField
    private Boolean isShow;

    /**
     * 文章导图
     */
    @TableField
    private String picUrl;

    @TableField
    private String thumbPicUrl;

    /**
     * 阅读次数
     */
    @TableField
    private Integer readCount;

    /**
     * 添加时间
     */
    @JSONField(serialize = false, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;


    /**
     * 内容
     */
    @TableField(exist = false)
    private String content;


    /**
     * 是否为推荐文章
     */
    @TableField
    private Boolean isRecommend;

    /**
     * 文章摘要
     */
    @TableField
    private String summary;

}