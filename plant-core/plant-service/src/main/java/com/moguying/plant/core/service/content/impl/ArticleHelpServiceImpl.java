package com.moguying.plant.core.service.content.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.content.ArticleHelpDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.ArticleHelp;
import com.moguying.plant.core.entity.content.ArticleHelpList;
import com.moguying.plant.core.service.content.ArticleHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleHelpServiceImpl implements ArticleHelpService {

    @Autowired
    private ArticleHelpDAO articleHelpDAO;

    @Override
    @DS("read")
    public PageResult<ArticleHelpList> articleHelpList(Integer page, Integer size, ArticleHelp where) {
        List<ArticleHelpList> helpLists = articleHelpDAO.articleHelpList(where.getTypeId());
        IPage<ArticleHelp> pageResult = articleHelpDAO.selectSelective(new Page(page, size), where);
        List<ArticleHelp> records = pageResult.getRecords();
        helpLists.forEach(articleHelpList -> {
            List<ArticleHelp> list = new ArrayList<>();
            records.stream()
                    .filter(articleHelp -> articleHelpList.getTitle().equals(articleHelp.getTitle()))
                    .forEach(list::add);
            articleHelpList.setList(list);
        });
        helpLists.removeIf(articleHelpList -> articleHelpList.getList() == null || articleHelpList.getList().isEmpty());
        return new PageResult<>(pageResult.getTotal(), helpLists);
    }

    @Override
    @DS("write")
    @Transactional
    public Integer addOrModifyArticleHelp(ArticleHelp help) {
        if (help == null) return 0;
        if (help.getId() == null) {
            help.setAddTime(new Date());
            return articleHelpDAO.insert(help);
        }
        if (articleHelpDAO.selectById(help.getId()) == null)
            return 0;
        help.setUpdateTime(new Date());
        return articleHelpDAO.updateById(help);
    }

    @Override
    @DS("write")
    @Transactional
    public Integer deleteArticleHelp(Integer id) {
        return articleHelpDAO.deleteById(id);
    }

    @Override
    @DS("read")
    public ArticleHelp getArticleHelp(Integer id) {
        return articleHelpDAO.selectById(id);
    }
}
