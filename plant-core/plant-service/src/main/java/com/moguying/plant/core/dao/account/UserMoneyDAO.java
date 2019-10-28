package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.account.UserMoney;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserMoneyDAO继承基类
 */
@Repository
public interface UserMoneyDAO extends BaseDAO<UserMoney> {

    IPage<UserMoney> selectSelective(Page<UserMoney> page, @Param("wq") UserMoney money);
    List<UserMoney> selectSelective(@Param("wq") UserMoney money);

    /**
     * 更新资金
     */
    Integer updateByPrimaryKeySelective(UserMoney userMoney);
}