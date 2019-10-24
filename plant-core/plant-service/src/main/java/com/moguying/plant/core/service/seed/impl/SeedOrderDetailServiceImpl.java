package com.moguying.plant.core.service.seed.impl;

import com.moguying.plant.constant.FertilizerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SeedEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SeedOrderDetailServiceImpl implements SeedOrderDetailService {

    @Value("${order.expire.time}")
    private Integer orderExpireTime;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO ;

    @Autowired
    private SeedDAO seedDAO;

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedOrderDetail> seedOrderDetailList(Integer page, Integer size, SeedOrderDetail where) {
        seedOrderDetailDAO.selectSelective(where);
        return null;
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedOrderDetail> userSeedOrderList(Integer page,Integer size,Integer userId,Integer state) {
        seedOrderDetailDAO.selectListByUserIdAndState(userId,state);
        return null;

    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedOrderDetail> selectUserPayListByUserId(Integer page, Integer size, Integer userId) {
        seedOrderDetailDAO.selectUserPayListByUserId(userId);
        return null;
    }

    @Override
    @DataSource("read")
    public Integer seedCanPlantBlockId (Integer id) {
        return seedOrderDetailDAO.findPlantBlockIdById(id);
    }

    @Override
    @DataSource("read")
    public SeedOrderDetail orderDetailByIdAndUserId(Integer id,Integer userId) {
        return seedOrderDetailDAO.selectByIdAndUserIdWithSeedTypeInfo(id,userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DataSource(value = "write")
    public ResultData<Integer> seedOrderCancel(Integer id, Integer userId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        SeedOrderDetail detail = seedOrderDetailDAO.selectByIdAndUserIdWithSeedTypeInfo(id,userId);
        if(null == detail)
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS);
        if(detail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState()))
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_DETAIL_HAS_PAY);
        if(detail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_HAS_CLOSE.getState()))
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_DETAIL_HAS_CLOSE);
        //更新订单详情信息
        SeedOrderDetail update = new SeedOrderDetail();
        update.setId(id);
        update.setState(SeedEnum.SEED_ORDER_DETAIL_HAS_CLOSE.getState());
        update.setCloseTime(new Date());
        if(seedOrderDetailDAO.updateById(update) <= 0)
            return resultData;
        //更新菌包库存
        Seed seed = seedDAO.selectById(detail.getSeedId());
        if(null == seed) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);
        }
        Seed seedUpdate = new Seed();
        seedUpdate.setId(seed.getId());
        seedUpdate.setLeftCount(seed.getLeftCount() + detail.getBuyCount());
        if(seedDAO.updateById(seedUpdate) <= 0 ){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData;
        }
        //更新券状态
        userFertilizerDAO.updateStateByOrderNumber(detail.getOrderNumber(), FertilizerEnum.FERTILIZER_NO_USE.getState());

        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }

    @Override
    @DataSource(value = "read")
    public List<SeedOrderDetail> needPayOrderList() {
        return seedOrderDetailDAO.selectListByUserIdAndState(null,SeedEnum.SEED_ORDER_DETAIL_NEED_PAY.getState());
    }

    @Override
    @DataSource("read")
    public SeedOrderDetail selectByOrderNumber(String orderNumber) {
        return seedOrderDetailDAO.selectByOrderNumber(orderNumber);
    }

    @DataSource("read")
    @Override
    public void downloadExcel(Integer userId, PageSearch<SeedOrderDetail> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("菌包订单", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(seedOrderDetailDAO, search, search.getWhere().getClass(), downloadInfo)).start();
    }
}
