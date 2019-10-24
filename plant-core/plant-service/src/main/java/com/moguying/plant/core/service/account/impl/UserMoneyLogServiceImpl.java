package com.moguying.plant.core.service.account.impl;

import com.moguying.plant.constant.FieldEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.account.UserMoneyLogDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.dto.UserMoneyLog;
import com.moguying.plant.core.entity.vo.AccountMoneyLogInfo;
import com.moguying.plant.core.service.account.UserMoneyLogService;
import com.moguying.plant.core.service.common.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserMoneyLogServiceImpl implements UserMoneyLogService {

    @Autowired
    private UserMoneyLogDAO userMoneyLogDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Override
    @DataSource("write")
    public int addLog(UserMoneyLog userMoneyLog) {
        return userMoneyLogDAO.insert(userMoneyLog);
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<UserMoneyLog> moneyLogList(Integer page, Integer size, UserMoneyLog where) {
        userMoneyLogDAO.selectSelective(where);
        return null;
    }

    /**
     * 对收益求和
     * @param userId
     * @return
     */
    @Override
    @DataSource("read")
    public BigDecimal sumFieldAndType(FieldEnum field, Integer userId, List<MoneyOpEnum> opEnums) {
        return userMoneyLogDAO.fieldSumByTypeUserId(field.getField(),userId, opEnums,null,null);
    }


    /**
     * 用户查询个人资金明细
     * @param userId
     * @param where
     * @return
     */
    @Override
    @DataSource("read")
    public AccountMoneyLogInfo userMoneyLogInfo(Integer userId, AccountMoneyLogInfo where) {
        return null;
    }

    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<UserMoneyLog> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("资金日志", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(userMoneyLogDAO, search, UserMoneyLog.class, downloadInfo)).start();
    }
}
