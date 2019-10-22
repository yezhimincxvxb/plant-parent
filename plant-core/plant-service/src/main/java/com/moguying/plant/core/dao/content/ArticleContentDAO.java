package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.Article;
import com.moguying.plant.core.entity.dto.ArticleContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleContentDAO继承基类
 */
@Repository
public interface ArticleContentDAO extends BaseMapper<ArticleContent> {
    Integer deleteByArticleIds(List<Article> articles);
}