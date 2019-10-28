package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.Article;
import com.moguying.plant.core.entity.content.ArticleContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleContentDAO继承基类
 */
@Repository
public interface ArticleContentDAO extends BaseDAO<ArticleContent> {
    Integer deleteByArticleIds(List<Article> articles);
}