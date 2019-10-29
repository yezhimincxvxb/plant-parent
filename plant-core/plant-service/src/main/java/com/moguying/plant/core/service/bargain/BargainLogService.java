package com.moguying.plant.core.service.bargain;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;

public interface BargainLogService {

    Integer getBargainCount(Integer userId, Integer detailId);

    Integer getBargainCountToday(Integer userId);

    PageResult<BargainVo> helpLog(Integer page, Integer size, Integer shareId, Integer orderId);
}
