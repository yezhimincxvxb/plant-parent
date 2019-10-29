package com.moguying.plant.core.service.reap.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapFeeDAO;
import com.moguying.plant.core.dao.reap.ReapFeeParamDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.ReapFee;
import com.moguying.plant.core.entity.reap.ReapFeeParam;
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
    private ReapFeeParamDAO reapFeeParamDAO;


    @Autowired
    private ReapFeeDAO reapFeeDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Override
    @DS("write")
    @Transactional
    public ResultData<Integer> addReapFee(ReapFee reapFee) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        Reap reap = reapDAO.selectByIdAndUserId(reapFee.getReapId(),reapFee.getUserId());
        if(null == reap) return  resultData;
        //获取到指定渠道的结算比
        ReapFeeParam where = new ReapFeeParam();
        where.setInviteUid(reapFee.getInviteUid());
        where.setSeedType(reap.getSeedType());
        ReapFeeParam reapFeeParam = reapFeeParamDAO.selectOne(new QueryWrapper<>(where));
        if(null == reapFeeParam) return resultData;
        Integer isFirst = reapDAO.countByUserIdAndGrowUpDays(reapFee.getUserId(),reap.getSeedGrowDays());
        BigDecimal feeAmount;
        if (isFirst == 1) {
            feeAmount = reap.getPreAmount().multiply(reapFeeParam.getFirstPlantRate());
            reapFee.setIsFirst(true);
        } else {
            feeAmount = reap.getPreAmount().multiply(reapFeeParam.getPlantRate());
        }
        feeAmount = InterestUtil.INSTANCE.divide(feeAmount, new BigDecimal("100.00"));
        reapFee.setFeeAmount(feeAmount);
        if(reapFeeDAO.insert(reapFee) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @DS("read")
    @Override
    public PageResult<ReapFee> reapFeeList(Integer page, Integer size, ReapFee where) {
        IPage<ReapFee> pageResult = reapFeeDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }


    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<ReapFee> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("渠道费用",request.getServletContext(),userId,downloadDir);
        new Thread(new DownloadService<>(reapFeeDAO,search, ReapFee.class,downloadInfo)).start();
    }




}
