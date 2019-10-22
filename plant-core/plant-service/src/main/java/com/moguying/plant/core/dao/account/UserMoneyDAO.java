package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.UserMoney;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMoneyDAO继承基类
 */
@Repository
public interface UserMoneyDAO extends BaseMapper<UserMoney> {

    List<UserMoney> selectSelective(UserMoney money);

    /**
     * 更新资金
     */
    Integer updateByPrimaryKeySelective(UserMoney userMoney);
}