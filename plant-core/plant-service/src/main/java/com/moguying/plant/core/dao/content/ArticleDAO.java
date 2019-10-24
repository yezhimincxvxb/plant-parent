package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.content.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleDAO继承基类
 */
@Repository
public interface ArticleDAO extends BaseMapper<Article> {
    List<Article> selectSelective(Article where);
    Integer deleteByTypeId(Integer id);
    Article selectByPrimaryKeyWithContent(Integer id);
}