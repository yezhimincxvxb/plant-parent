package com.moguying.plant.core.service.account.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.FieldEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.dao.account.UserMoneyLogDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.account.UserMoneyLog;
import com.moguying.plant.core.entity.account.vo.AccountMoneyLogInfo;
import com.moguying.plant.core.entity.index.TotalTable;
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
    @DS("write")
    public int addLog(UserMoneyLog userMoneyLog) {
        return userMoneyLogDAO.insert(userMoneyLog);
    }

    @Override
    @DS("read")
    public PageResult<UserMoneyLog> moneyLogList(Integer page, Integer size, UserMoneyLog where) {
        IPage<UserMoneyLog> pageResult = userMoneyLogDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 对收益求和
     *
     * @param userId
     * @return
     */
    @Override
    @DS("read")
    public BigDecimal sumFieldAndType(FieldEnum field, Integer userId, List<MoneyOpEnum> opEnums) {
        return userMoneyLogDAO.fieldSumByTypeUserId(field.getField(), userId, opEnums, null, null);
    }


    /**
     * 用户查询个人资金明细
     *
     * @param userId
     * @param where
     * @return
     */
    @Override
    @DS("read")
    public AccountMoneyLogInfo userMoneyLogInfo(Integer userId, AccountMoneyLogInfo where) {
        return null;
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<UserMoneyLog> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("资金日志", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(userMoneyLogDAO, search, UserMoneyLog.class, downloadInfo)).start();
    }

    @Override
    @DS("read")
    public TotalTable getUserMoney(Integer state) {
        return userMoneyLogDAO.getUserMoney(state);
    }
}
