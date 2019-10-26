package com.moguying.plant.core.service.account;

import com.moguying.plant.constant.FieldEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.account.UserMoneyLog;
import com.moguying.plant.core.entity.account.vo.AccountMoneyLogInfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;


public interface UserMoneyLogService {

    PageResult<UserMoneyLog> moneyLogList(Integer page, Integer size, UserMoneyLog where);

    int addLog(UserMoneyLog userMoneyLog);

    BigDecimal sumFieldAndType(FieldEnum field, Integer userId, List<MoneyOpEnum> opEnums);

    AccountMoneyLogInfo userMoneyLogInfo(Integer userId, AccountMoneyLogInfo where);

    void downloadExcel(Integer userId, PageSearch<UserMoneyLog> search, HttpServletRequest request);

}
