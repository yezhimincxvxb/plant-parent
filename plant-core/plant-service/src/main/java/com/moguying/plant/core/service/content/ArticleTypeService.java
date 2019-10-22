package com.moguying.plant.core.service.content;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.ArticleType;

import java.util.List;


public interface ArticleTypeService {

    @DataSource("read")
    List<ArticleType> articleTypeList();

    @DataSource("write")
    ResultData<Integer> addArticleType(ArticleType articleType);

    @DataSource("write")
    ResultData<Integer> deleteArticleType(Integer id);

    @DataSource("write")
    Integer updateArticleType(Integer id, ArticleType articleType);

    @DataSource("read")
    ArticleType selectTypeByUrlName(String urlName);

}
