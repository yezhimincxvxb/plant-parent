package com.moguying.plant.core.entity.content;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_article
 * @author 
 */
@Data
public class Article implements Serializable {
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章分类
     */
    private Integer typeId;

    private String typeName;

    private String typeUrlName;


    /**
     * seo关键字
     */
    private String seoKey;

    /**
     * 发布时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 是否显示[0否，1是]
     */
    @JSONField
    private Boolean isShow;

    /**
     * 文章导图
     */
    private String picUrl;

    private String thumbPicUrl;

    /**
     * 阅读次数
     */
    private Integer readCount;

    /**
     * 添加时间
     */
    @JSONField(serialize = false,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;


    /**
     * 内容
     */
    private String content;


    /**
     * 是否为推荐文章
     */
    private Boolean isRecommend;

    /**
     * 文章摘要
     */
    private String summary;


    private static final long serialVersionUID = 1L;

}