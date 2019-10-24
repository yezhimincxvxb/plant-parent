package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MoneyRechargeDAO继承基类
 */
@Repository
public interface MoneyRechargeDAO extends BaseMapper<MoneyRecharge> {
    List<MoneyRecharge> selectSelective(MoneyRecharge where);

    MoneyRecharge selectByPrimaryKey(Integer id);
}