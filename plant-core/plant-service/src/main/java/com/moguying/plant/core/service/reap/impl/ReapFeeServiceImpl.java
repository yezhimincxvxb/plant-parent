package com.moguying.plant.core.service.reap.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapFeeDAO;
import com.moguying.plant.core.dao.seed.SeedDaysDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.ReapFee;
import com.moguying.plant.core.entity.seed.SeedDays;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.reap.ReapFeeService;
import com.moguying.plant.utils.InterestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
public class ReapFeeServiceImpl implements ReapFeeService {

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private SeedDaysDAO seedDaysDAO;


    @Autowired
    private ReapFeeDAO reapFeeDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Override
    @DataSource("write")
    @Transactional
    public ResultData<Integer> addReapFee(ReapFee reapFee) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        Reap reap = reapDAO.selectByIdAndUserId(reapFee.getReapId(),reapFee.getUserId());
        if(null == reap) return  resultData;
        Integer isFirst = reapDAO.countByUserIdAndGrowUpDays(reapFee.getUserId(),reap.getSeedGrowDays());
        SeedDays seedDays = seedDaysDAO.selectById(reap.getSeedGrowDays());
        BigDecimal feeAmount;
        if (isFirst == 1) {
            feeAmount = reap.getPreAmount().multiply(seedDays.getFirstPlantRate());
            reapFee.setIsFirst(true);
        } else {
            feeAmount = reap.getPreAmount().multiply(seedDays.getPlantRate());
        }
        feeAmount = InterestUtil.INSTANCE.divide(feeAmount, new BigDecimal("100.00"));
        reapFee.setFeeAmount(feeAmount);
        if(reapFeeDAO.insert(reapFee) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @DataSource("read")
    @Override
    @Pagination
    public PageResult<ReapFee> reapFeeList(Integer page, Integer size, ReapFee where) {
        reapFeeDAO.selectSelective(where);
        return null;
    }


    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<ReapFee> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("渠道费用",request.getServletContext(),userId,downloadDir);
        new Thread(new DownloadService<>(reapFeeDAO,search, ReapFee.class,downloadInfo)).start();
    }




}
