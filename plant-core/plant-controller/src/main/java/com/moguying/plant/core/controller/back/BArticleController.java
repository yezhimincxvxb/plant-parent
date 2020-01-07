package com.moguying.plant.core.controller.back;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
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
@Api(tags = "文章管理")
public class BArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleTypeService articleTypeService;
    @Autowired
    private ArticleHelpService articleHelpService;


    @PostMapping
    @ApiOperation("添加文章")
    public ResponseData<Integer> addArticle(@RequestBody Article article) {
        if (articleService.addArticle(article) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除文章")
    public ResponseData<Integer> deleteArticle(@PathVariable Integer id) {
        if (articleService.deleteArticle(id) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    @PostMapping(value = "/list")
    @ApiOperation("文章列表")
    public PageResult<Article> articleList(@RequestBody PageSearch<Article> search) {
        if (null == search.getWhere())
            search.setWhere(new Article());
        return articleService.articleList(search.getPage(), search.getSize(), search.getWhere());
    }


    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个文章的内容")
    public ResponseData<Article> getArticle(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleService.getArticle(id));
    }


    @PutMapping(value = "/{id}")
    @ApiOperation("更新文章")
    public ResponseData<Integer> updateArticle(@PathVariable Integer id, @RequestBody Article update) {
        ResultData<Integer> resultData = articleService.updateArticle(id, update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    @GetMapping(value = "/type")
    @ApiOperation("文章分类列表")
    public ResponseData<List<ArticleType>> typeList(@RequestParam(value = "state", required = false) Integer state) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleTypeService.articleTypeList(state));
    }


    @PostMapping(value = "/type")
    @ApiOperation("添加文章类型")
    public ResponseData<Integer> addArticleType(@RequestBody ArticleType type) {
        ResultData<Integer> resultData = articleTypeService.addArticleType(type);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    @PutMapping(value = "/type/{id}")
    @ApiOperation("更新文章类型")
    public ResponseData<Integer> updateArticleType(@PathVariable Integer id, @RequestBody ArticleType type) {
        if (articleTypeService.updateArticleType(id, type) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    @DeleteMapping("/type/{id}")
    @ApiOperation("删除文章类型")
    public ResponseData<Integer> deleteArticleType(@PathVariable Integer id) {
        ResultData<Integer> resultData = articleTypeService.deleteArticleType(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    @PostMapping("/help")
    @ApiOperation("添加帮助信息")
    public ResponseData<Integer> addArticleHelp(@RequestBody ArticleHelp help) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleHelpService.addOrModifyArticleHelp(help));
    }


    @PutMapping("/help/{id}")
    @ApiOperation("修改帮助信息")
    public ResponseData<Integer> modifyArticleHelp(@RequestBody ArticleHelp help, @PathVariable("id") Integer id) {
        if (id < 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleHelpService.addOrModifyArticleHelp(help.setId(id)));
    }


    @DeleteMapping("/help/{id}")
    @ApiOperation("删除帮助信息")
    public ResponseData<Integer> modifyArticleHelp(@PathVariable("id") Integer id) {
        if (id < 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleHelpService.deleteArticleHelp(id));
    }

    @PostMapping("/help/{urlName}")
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
                .setTypeId(type.getId());
        return articleHelpService.articleHelpList(search.getPage(), search.getSize(), where);
    }


    @GetMapping("/help/{id}")
    @ApiOperation("帮助信息详情")
    public ResponseData<ArticleHelp> getArticleHelp(@PathVariable("id") Integer id) {
        if (id < 0)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), articleHelpService.getArticleHelp(id));
    }


}
