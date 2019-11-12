package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.reap.ReapExcLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReapExcLogDAO extends BaseDAO<ReapExcLog> {

    IPage<ReapExcLog> selectSelective(Page page,@Param("wq") ReapExcLog where);
}
