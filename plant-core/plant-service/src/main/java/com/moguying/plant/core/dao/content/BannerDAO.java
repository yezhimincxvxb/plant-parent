package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BannerDAO继承基类
 */
@Repository
public interface BannerDAO extends BaseDAO<Banner> {
    IPage<Banner> selectSelective(Page<Banner> page, @Param("wq") Banner where);

    List<Banner> bannerListForHome(Integer type);
}