package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MoneyRechargeDAO继承基类
 */
@Repository
public interface MoneyRechargeDAO extends BaseDAO<MoneyRecharge> {

    List<MoneyRecharge> selectSelective(MoneyRecharge where);

    MoneyRecharge selectByPrimaryKey(Integer id);


}