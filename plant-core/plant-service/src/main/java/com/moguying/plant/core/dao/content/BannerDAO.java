package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.Banner;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BannerDAO继承基类
 */
@Repository
public interface BannerDAO extends BaseMapper<Banner> {
    List<Banner> selectSelective(Banner where);
    List<Banner> bannerListForHome(Integer type);
}