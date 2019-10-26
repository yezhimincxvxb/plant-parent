package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;

/**
 * 用户-蘑菇币服务层
 */
public interface SaleCoinService {

    /**
     * id查询
     */
    
    SaleCoin findById(Integer userId);

    /**
     * 添加用户-蘑菇币信息
     */
    
    int insertSaleCoin(SaleCoin saleCoin);

    /**
     * 更新蘑菇币
     */
    
    UserSaleCoin updateSaleCoin(UserSaleCoin saleCoin);

}
