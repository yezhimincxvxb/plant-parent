package com.moguying.plant.core.entity.content;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_article_type
 * @author 
 */
@Data
public class ArticleType implements Serializable {
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 链接跳转名英文
     */
    private String urlName;

    /**
     * 排序
     */
    private Integer orderNumber;

    private static final long serialVersionUID = 1L;
}