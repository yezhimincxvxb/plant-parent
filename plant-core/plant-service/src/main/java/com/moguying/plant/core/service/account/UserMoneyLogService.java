package com.moguying.plant.core.service.account;

import com.moguying.plant.constant.FieldEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.dto.UserMoneyLog;
import com.moguying.plant.core.entity.vo.AccountMoneyLogInfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;


public interface UserMoneyLogService {

    @DataSource("read")
    PageResult<UserMoneyLog> moneyLogList(Integer page, Integer size, UserMoneyLog where);

    @DataSource("write")
    int addLog(UserMoneyLog userMoneyLog);

    @DataSource("read")
    BigDecimal sumFieldAndType(FieldEnum field, Integer userId, List<MoneyOpEnum> opEnums);

    @DataSource("read")
    AccountMoneyLogInfo userMoneyLogInfo(Integer userId, AccountMoneyLogInfo where);

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<UserMoneyLog> search, HttpServletRequest request);

}
