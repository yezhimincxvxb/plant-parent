package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.content.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleDAO继承基类
 */
@Repository
public interface ArticleDAO extends BaseMapper<Article> {
    IPage<Article> selectSelective(Page<Article> page , @Param("wq") Article where);
    Integer deleteByTypeId(Integer id);
    Article selectByPrimaryKeyWithContent(Integer id);
}