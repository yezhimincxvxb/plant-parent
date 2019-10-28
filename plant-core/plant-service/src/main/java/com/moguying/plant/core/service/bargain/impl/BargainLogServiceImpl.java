package com.moguying.plant.core.service.bargain.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.bargain.BargainLogDao;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.vo.BargainResponse;
import com.moguying.plant.core.service.bargain.BargainLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BargainLogServiceImpl implements BargainLogService {

    @Autowired
    private BargainLogDao bargainLogDao;

    @Override
    @DS("read")
    public Integer getBargainCount(Integer userId, Integer shareId, Integer productId) {
        return bargainLogDao.getBargainCount(userId, shareId, productId);
    }

    @Override
    @DS("read")
    public Integer getBargainCountToday(Integer userId) {
        return bargainLogDao.getBargainCountToday(userId);
    }

    @Override
    @DS("read")
    public PageResult<BargainResponse> helpLog(Integer page, Integer size, Integer shareId, Integer productId) {
        IPage<BargainResponse> pageResult = bargainLogDao.helpLog(new Page<>(page, size), shareId, productId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }
}
