package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Article;

import java.util.List;

public interface ArticleService {


    Integer addArticle(Article article);


    PageResult<Article> articleList(Integer page, Integer size, Article where);


    Integer deleteArticle(Integer id);


    ResultData<Integer> updateArticle(Integer id, Article update);


    Article getArticle(Integer id);


    List<Article> articleForHome();


}
