package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;

/**
 * 用户-蘑菇币服务层
 */
public interface SaleCoinService {

    /**
     * id查询
     */
    @DataSource("read")
    SaleCoin findById(Integer userId);

    /**
     * 添加用户-蘑菇币信息
     */
    @DataSource("write")
    int insertSaleCoin(SaleCoin saleCoin);

    /**
     * 更新蘑菇币
     */
    @DataSource("write")
    UserSaleCoin updateSaleCoin(UserSaleCoin saleCoin);

}
