package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.ArticleHelp;
import com.moguying.plant.core.entity.content.ArticleHelpList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleHelpDAO extends BaseDAO<ArticleHelp> {

    IPage<ArticleHelp> selectSelective(Page page, @Param("wq") ArticleHelp wq);

    List<ArticleHelpList> articleHelpList(Integer typeId);
}