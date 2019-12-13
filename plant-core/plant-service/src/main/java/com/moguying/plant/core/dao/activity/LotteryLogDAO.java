package com.moguying.plant.core.dao.activity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.activity.LotteryLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryLogDAO extends BaseDAO<LotteryLog> {

    IPage<LotteryLog> selectSelective(Page<LotteryLog> page, @Param("wq") LotteryLog where);

    @Override
    List<LotteryLog> selectSelective(LotteryLog where);
}
