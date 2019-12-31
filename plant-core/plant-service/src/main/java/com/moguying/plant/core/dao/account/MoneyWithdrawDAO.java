package com.moguying.plant.core.dao.account;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
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
public interface MoneyWithdrawDAO extends BaseDAO<MoneyWithdraw> {
    IPage<MoneyWithdraw> selectSelective(Page<MoneyWithdraw> page, @Param("wq") MoneyWithdraw where);

    List<MoneyWithdraw> selectSelective(@Param("wq") MoneyWithdraw where);

    MoneyWithdraw selectByOrderNumber(String orderNumber);

    BigDecimal withdrawDailyCountByUserId(@Param("userId") Integer userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal getWithdrawalSuccess(@Param("state") Integer state);

    BigDecimal getWithdrawalWait(@Param("state") Integer state);
}