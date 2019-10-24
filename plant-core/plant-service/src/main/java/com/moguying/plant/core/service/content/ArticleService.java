package com.moguying.plant.core.service.content;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Article;

import java.util.List;

public interface ArticleService  {

    @DataSource("write")
    Integer addArticle(Article article);

    @DataSource("read")
    PageResult<Article> articleList(Integer page, Integer size, Article where);

    @DataSource("write")
    Integer deleteArticle(Integer id);

    @DataSource("write")
    ResultData<Integer> updateArticle(Integer id, Article update);

    @DataSource("read")
    Article getArticle(Integer id);

    @DataSource("read")
    List<Article> articleForHome();


}
