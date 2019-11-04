package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.reap.ReapFeeParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReapFeeParamDAO extends BaseDAO<ReapFeeParam> {

    IPage<ReapFeeParam> selectSelective(Page page, ReapFeeParam where);
}
