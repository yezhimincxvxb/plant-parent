package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.ReapFee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReapFeeDAO继承基类
 */
@Repository
public interface ReapFeeDAO extends BaseMapper<ReapFee> {
    List<ReapFee> selectSelective(ReapFee where);

}