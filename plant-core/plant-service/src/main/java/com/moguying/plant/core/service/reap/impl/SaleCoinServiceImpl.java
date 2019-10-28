package com.moguying.plant.core.service.reap.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.reap.SaleCoinDao;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;
import com.moguying.plant.core.service.reap.SaleCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户-蘑菇币实现类
 */
@Service
public class SaleCoinServiceImpl implements SaleCoinService {

    @Autowired
    private SaleCoinDao saleCoinDao;

    @Override
    public SaleCoin findById(Integer userId) {
        return saleCoinDao.selectById(userId);
    }

    @Override
    @DS("write")
    public int insertSaleCoin(SaleCoin saleCoin) {
        return saleCoinDao.insert(saleCoin);
    }

    @Override
    @DS("write")
    public UserSaleCoin updateSaleCoin(UserSaleCoin userSaleCoin) {
        SaleCoin saleCoin = userSaleCoin.getSaleCoin();
        if (saleCoinDao.updateById(saleCoin) <= 0) return null;
        return userSaleCoin;
    }
}
