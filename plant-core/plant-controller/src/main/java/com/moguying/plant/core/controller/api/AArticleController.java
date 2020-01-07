package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.content.Article;
import com.moguying.plant.core.entity.content.ArticleHelp;
import com.moguying.plant.core.entity.content.ArticleHelpList;
import com.moguying.plant.core.entity.content.ArticleType;
import com.moguying.plant.core.service.content.ArticleHelpService;
import com.moguying.plant.core.service.content.ArticleService;
import com.moguying.plant.core.service.content.ArticleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api(tags = "文章内容")
public class AArticleController {

    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleHelpService articleHelpService;

    @GetMapping("/types")
    @NoLogin
    @ApiOperation("文章分类")
    public ResponseData<List<ArticleType>> articleTypes(@RequestParam(value = "state", required = false) Integer state) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleTypeService.articleTypeList(state));
    }


    @PostMapping("/{urlName}")
    @NoLogin
    @ApiOperation("对应分类文章列表")
    public PageResult<Article> articleList(@RequestBody PageSearch<Article> search, @PathVariable String urlName) {
        PageResult<Article> list = new PageResult<>();
        ArticleType type = articleTypeService.selectTypeByUrlName(urlName);
        if (null == type)
            return list.setMessage(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getMessage()).setStatus(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getState());
        Article where = new Article();
        if (null != search.getWhere())
            where = search.getWhere();
        where.setIsShow(true);
        where.setTypeId(type.getId());
        return articleService.articleList(search.getPage(), search.getSize(), where);
    }


    @GetMapping("/{urlName}/{id}")
    @NoLogin
    @ApiOperation("文章详情")
    public ResponseData<Article> articleDetail(@PathVariable String urlName, @PathVariable Integer id) {
        ResponseData<Article> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        ArticleType type = articleTypeService.selectTypeByUrlName(urlName);
        if (null == type)
            return responseData.setMessage(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getMessage())
                    .setState(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getState());
        Article article = articleService.getArticle(id);
        if (null == article)
            return responseData.setMessage(MessageEnum.ARTICLE_NOT_EXISTS.getMessage())
                    .setState(MessageEnum.ARTICLE_NOT_EXISTS.getState());
        return responseData.setData(article);
    }

    @PostMapping("/help/{urlName}")
    @NoLogin
    @ApiOperation("不同分类的帮助信息列表")
    public PageResult<ArticleHelpList> articleHelpList(@RequestBody PageSearch<ArticleHelp> search,
                                                       @PathVariable("urlName") String urlName) {
        PageResult<ArticleHelpList> responseData = new PageResult<>();
        ArticleType type = articleTypeService.selectTypeByUrlName(urlName);
        if (null == type)
            return responseData.setMessage(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getMessage())
                    .setStatus(MessageEnum.ARTICLE_TYPE_NOT_EXIST.getState());
        if (search.getWhere() == null) search.setWhere(new ArticleHelp());
        ArticleHelp where = search.getWhere()
                .setTypeId(type.getId())
                .setIsShow(true);
        return articleHelpService.articleHelpList(search.getPage(), search.getSize(), where);
    }


}
