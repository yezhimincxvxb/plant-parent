package com.moguying.plant.core.dao.bargain;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BargainLogDao extends BaseMapper<BargainLog> {

    Integer getBargainCount(@Param("userId") Integer userId, @Param("detailId") Integer detailId);

    Integer getBargainCountToday(@Param("userId") Integer userId);

    IPage<BargainVo> helpLog(Page<BargainVo> page, @Param("shareId") Integer shareId, @Param("orderId") Integer orderId);
}
