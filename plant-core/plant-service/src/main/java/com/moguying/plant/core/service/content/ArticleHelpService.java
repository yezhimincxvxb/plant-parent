package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.ArticleHelp;
import com.moguying.plant.core.entity.content.ArticleHelpList;

public interface ArticleHelpService {

    PageResult<ArticleHelpList> articleHelpList(Integer page, Integer size, ArticleHelp where);

    Integer addOrModifyArticleHelp(ArticleHelp help);

    Integer deleteArticleHelp(Integer id);

    ArticleHelp getArticleHelp(Integer id);
}
