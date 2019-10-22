package com.moguying.plant.core.service.reap.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.reap.SaleCoinDao;
import com.moguying.plant.core.entity.dto.SaleCoin;
import com.moguying.plant.core.entity.dto.UserSaleCoin;
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
        return saleCoinDao.findById(userId);
    }

    @Override
    @DataSource("write")
    public int insertSaleCoin(SaleCoin saleCoin) {
        return saleCoinDao.insertSaleCoin(saleCoin);
    }

    @Override
    @DataSource("write")
    public UserSaleCoin updateSaleCoin(UserSaleCoin userSaleCoin) {
        SaleCoin saleCoin = userSaleCoin.getSaleCoin();
        if (saleCoinDao.updateSaleCoin(saleCoin) <= 0) return null;
        return userSaleCoin;
    }
}
