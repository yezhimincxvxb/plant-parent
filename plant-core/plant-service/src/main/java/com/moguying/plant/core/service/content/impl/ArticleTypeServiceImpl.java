package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.content.ArticleContentDAO;
import com.moguying.plant.core.dao.content.ArticleDAO;
import com.moguying.plant.core.dao.content.ArticleTypeDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Article;
import com.moguying.plant.core.entity.dto.ArticleType;
import com.moguying.plant.core.service.content.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Autowired
    private ArticleTypeDAO articleTypeDAO;

    @Autowired
    private ArticleContentDAO articleContentDAO;


    @Autowired
    private ArticleDAO articleDAO;

    @Override
    @DataSource("read")
    public List<ArticleType> articleTypeList() {
        return articleTypeDAO.selectSelective();
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> addArticleType(ArticleType articleType) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(null != articleTypeDAO.selectByUrlName(articleType.getUrlName()))
            return resultData;
       if(articleTypeDAO.insert(articleType) > 0)
           return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(articleType.getId());
       return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> deleteArticleType(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.SUCCESS,0);
        if(null == articleTypeDAO.selectById(id))
            return resultData.setMessageEnum(MessageEnum.ARTICLE_TYPE_NOT_EXIST);
        else if (articleTypeDAO.deleteById(id) <= 0)
            return resultData.setMessageEnum(MessageEnum.ERROR);

        Article where = new Article();
        where.setTypeId(id);
        List<Article> articles = articleDAO.selectSelective(where);
        if(articles.size() > 0){
            if(articleContentDAO.deleteByArticleIds(articles) <= 0)
                return resultData.setMessageEnum(MessageEnum.DELETE_ARTICLE_CONTENT_FAIL);
            if(articleDAO.deleteByTypeId(id) <= 0){
                return resultData.setMessageEnum(MessageEnum.DELETE_ARTICLE_FAIL);
            }
        }
        return resultData;

    }

    @Override
    @DataSource("write")
    public Integer updateArticleType(Integer id,ArticleType update) {
        ArticleType type = articleTypeDAO.selectById(id);
        if(type != null){
            update.setId(id);
            return articleTypeDAO.updateById(update);
        }
        return -1;
    }


    @Override
    @DataSource("read")
    public ArticleType selectTypeByUrlName(String urlName) {
        return articleTypeDAO.selectByUrlName(urlName);
    }
}
