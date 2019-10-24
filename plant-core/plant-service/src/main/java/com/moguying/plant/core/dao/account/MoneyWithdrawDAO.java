package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.account.MoneyWithdraw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * MoneyWithdrawDAO继承基类
 */
@Repository
public interface MoneyWithdrawDAO extends BaseMapper<MoneyWithdraw> {
    List<MoneyWithdraw> selectSelective(MoneyWithdraw where);
    List<MoneyWithdraw> selectiveForApi(MoneyWithdraw where);
    MoneyWithdraw selectByOrderNumber(String orderNumber);
    BigDecimal withdrawDailyCountByUserId(@Param("userId") Integer userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}