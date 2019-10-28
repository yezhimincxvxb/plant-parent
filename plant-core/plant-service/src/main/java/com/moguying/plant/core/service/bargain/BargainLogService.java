package com.moguying.plant.core.service.bargain;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.vo.BargainResponse;

public interface BargainLogService {

    Integer getBargainCount(Integer userId, Integer shareId, Integer productId);

    Integer getBargainCountToday(Integer userId);

    PageResult<BargainResponse> helpLog(Integer page, Integer size, Integer shareId, Integer productId);
}
