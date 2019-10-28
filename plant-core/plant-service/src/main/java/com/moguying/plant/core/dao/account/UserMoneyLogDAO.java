package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.account.UserMoneyLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * UserMoneyLogDAO继承基类
 */
@Repository
public interface UserMoneyLogDAO extends BaseDAO<UserMoneyLog> {
    IPage<UserMoneyLog> selectSelective(Page<UserMoneyLog> page, @Param("wq") UserMoneyLog where);
    List<UserMoneyLog> selectSelective(@Param("wq") UserMoneyLog where);
    BigDecimal fieldSumByTypeUserId(@Param("field") String field, @Param("userId") Integer userId,
                                    @Param("types") List<MoneyOpEnum> types,
                                    @Param("startTime") Date startTime,
                                    @Param("endTime") Date endTime);
}