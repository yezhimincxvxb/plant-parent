package com.moguying.plant.core.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_article_content
 * @author 
 */
@Data
public class ArticleContent implements Serializable {
    private Integer articleId;

    private String content;

    private static final long serialVersionUID = 1L;

}