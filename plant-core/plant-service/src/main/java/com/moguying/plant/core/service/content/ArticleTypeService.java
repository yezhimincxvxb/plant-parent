package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.ArticleType;

import java.util.List;


public interface ArticleTypeService {

    
    List<ArticleType> articleTypeList();

    
    ResultData<Integer> addArticleType(ArticleType articleType);

    
    ResultData<Integer> deleteArticleType(Integer id);

    
    Integer updateArticleType(Integer id, ArticleType articleType);

    
    ArticleType selectTypeByUrlName(String urlName);

}
