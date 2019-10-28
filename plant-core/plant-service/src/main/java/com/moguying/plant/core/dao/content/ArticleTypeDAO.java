package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.ArticleType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ArticleTypeDAO继承基类
 */
@Repository
public interface ArticleTypeDAO extends BaseDAO<ArticleType> {

    List<ArticleType> selectSelective();

    ArticleType selectByUrlName(String urlName);
}