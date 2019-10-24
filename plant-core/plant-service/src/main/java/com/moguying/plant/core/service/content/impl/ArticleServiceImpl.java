package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.content.ArticleContentDAO;
import com.moguying.plant.core.dao.content.ArticleDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Article;
import com.moguying.plant.core.entity.dto.ArticleContent;
import com.moguying.plant.core.service.content.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleContentDAO articleContentDAO;

    @Override
    @DataSource("write")
    public Integer addArticle(Article article) {
        article.setAddTime(new Date());
        if(articleDAO.insert(article) > 0) {
            ArticleContent content = new ArticleContent();
            content.setArticleId(article.getId());
            content.setContent(article.getContent());
            articleContentDAO.insert(content);
            return article.getId();
        }
        return -1;
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<Article> articleList(Integer page, Integer size, Article where) {
        articleDAO.selectSelective(where);
        return null;
    }

    @Override
    @DataSource("write")
    public Integer deleteArticle(Integer id) {
        if(articleDAO.deleteById(id) > 0 && articleContentDAO.deleteById(id) > 0){
            return 1;
        }
        return  -1;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> updateArticle(Integer id, Article update) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.SUCCESS,0);
        Article article = articleDAO.selectById(id);
        if(article != null){
            update.setId(id);
            if(articleDAO.updateById(update) <= 0 )
                return resultData.setMessageEnum(MessageEnum.UPDATE_ARTICLE_FAIL);
            if(update.getContent() != null){
                ArticleContent content = new ArticleContent();
                content.setArticleId(id);
                content.setContent(update.getContent());
                if( articleContentDAO.updateById(content) <=0 )
                    return resultData.setMessageEnum(MessageEnum.UPDATE_ARTICLE_CONTENT_FAIL);
            }
            return resultData;
        }
        return resultData.setMessageEnum(MessageEnum.ARTICLE_NOT_EXISTS);
    }

    @Override
    @DataSource("read")
    public Article getArticle(Integer id) {
        return articleDAO.selectByPrimaryKeyWithContent(id);
    }


    /**
     * 首页显示公告
     * @return
     */
    @Override
    @DataSource("read")
    public List<Article> articleForHome() {
        Article where = new Article();
        //公告类型，TODO 上线需改动
        where.setTypeId(1);
        where.setIsShow(true);
        return articleDAO.selectSelective(where);
    }
}
