package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.seed.SeedPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedPicDAO继承基类
 */
@Repository
public interface SeedPicDAO extends BaseMapper<SeedPic> {
    List<SeedPic> selectSelective(SeedPic seedPic);
    List<SeedPic> selectByRange(@Param("range") List<Integer> range);
}