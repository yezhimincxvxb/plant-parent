package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.reap.ReapFee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReapFeeDAO继承基类
 */
@Repository
public interface ReapFeeDAO extends BaseDAO<ReapFee> {
    IPage<ReapFee> selectSelective(Page<ReapFee> page, @Param("wq") ReapFee where);

    List<ReapFee> selectSelective(@Param("wq") ReapFee where);

}