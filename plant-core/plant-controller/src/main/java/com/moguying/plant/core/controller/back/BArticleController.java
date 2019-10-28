package com.moguying.plant.core.controller.back;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Article;
import com.moguying.plant.core.entity.content.ArticleType;
import com.moguying.plant.core.service.content.ArticleService;
import com.moguying.plant.core.service.content.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class BArticleController {


    @Autowired
    ArticleService articleService;


    @Autowired
    ArticleTypeService articleTypeService;

    /**
     * 添加文章
     * @param article
     * @return
     */
    @PostMapping
    public ResponseData<Integer> addArticle(@RequestBody Article article){
        if(articleService.addArticle(article) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData<Integer> deleteArticle(@PathVariable Integer id){
        if(articleService.deleteArticle(id) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }

    /**
     * 文章列表
     * @param search
     * @return
     */
    @PostMapping(value = "/list")
    public PageResult<Article> articleList(@RequestBody PageSearch<Article> search){
        if(null == search.getWhere())
                search.setWhere(new Article());
        return articleService.articleList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 获取单个文章的内容
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseData<Article> getArticle(@PathVariable Integer id){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),articleService.getArticle(id));
    }


    /**
     * 更新文章
     * @param id
     * @param update
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseData<Integer> updateArticle(@PathVariable Integer id, @RequestBody Article update){
        ResultData<Integer> resultData = articleService.updateArticle(id,update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }

    /**
     * 文章分类列表
     * @return
     */
    @GetMapping(value = "/type")
    public ResponseData<List<ArticleType>> typeList(){

       return  new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),articleTypeService.articleTypeList());
    }


    /**
     * 添加文章类型
     * @param type
     * @return
     */
    @PostMapping(value = "/type")
    public ResponseData<Integer> addArticleType(@RequestBody ArticleType type){
        ResultData<Integer> resultData = articleTypeService.addArticleType(type);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 更新文章类型
     * @param id
     * @param type
     * @return
     */
    @PutMapping(value = "/type/{id}")
    public ResponseData<Integer> updateArticleType(@PathVariable Integer id, @RequestBody ArticleType type){
        if(articleTypeService.updateArticleType(id,type) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    /**
     * 删除文章类型
     * @param id
     * @return
     */
    @DeleteMapping("/type/{id}")
    public ResponseData<Integer> deleteArticleType(@PathVariable Integer id){
        ResultData<Integer> resultData = articleTypeService.deleteArticleType(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }

}
