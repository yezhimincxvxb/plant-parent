package com.moguying.plant.core.entity.content;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * plant_article_content
 *
 * @author
 */
@Data
@TableName("plant_article_content")
public class ArticleContent implements Serializable {

    private static final long serialVersionUID = -4474775006950139757L;

    @TableId
    private Integer articleId;

    @TableField
    private String content;


}