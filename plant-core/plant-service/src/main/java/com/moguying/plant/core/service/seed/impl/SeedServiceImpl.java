package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SeedEnum;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.entity.DownloadInfo;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.common.vo.HomeSeed;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import com.moguying.plant.core.entity.seed.vo.SeedReview;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.seed.SeedService;
import com.moguying.plant.utils.InterestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class SeedServiceImpl implements SeedService {


    @Autowired
    private SeedDAO seedDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;


    private static Map<Boolean,String> markMap;
    static {
        markMap = new HashMap<>();
        markMap.put(true,"审核通过");
        markMap.put(false,"审核不通过");
    }


    @Override
    @DS("read")
    public PageResult<Seed> seedList(Integer page , Integer size , Seed seed) {
        IPage<Seed> pageResult = seedDAO.selectSelective(new Page<>(page, size), seed);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    /**
     * 初审菌包
     * @param id
     * @param seedReview
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> review(Integer id, SeedReview seedReview, Integer verifyUserId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        Seed reviewSeed = seedDAO.selectById(id);
        if(reviewSeed == null || reviewSeed.getState().equals(SeedEnum.CANCEL.getState()))
            return resultData.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);
        Integer state;
        if(seedReview.getState())
            state = SeedEnum.REVIEWED.getState();
        else
            state = SeedEnum.CANCEL.getState();

        if(reviewSeed.getState().equals(state))
            return resultData.setMessageEnum(MessageEnum.SEED_HAD_REVIEWED);
        Seed update = new Seed();
        update.setId(id);
        update.setState(state);
        if(StringUtils.isEmpty(seedReview.getMark()))
            update.setReviewMark(markMap.get(seedReview.getState()));
        else
            update.setReviewMark(seedReview.getMark());

        //审核通过计算收益
        if(seedReview.getState()) {
            //计算预期收益
            BigDecimal interest = InterestUtil.INSTANCE.calInterest(reviewSeed.getTotalAmount(), reviewSeed.getInterestRates(), reviewSeed.getGrowDays());
            update.setTotalInterest(interest);
            update.setLeftCount(reviewSeed.getTotalCount());
        }
        update.setReviewTime(new Date());
        update.setReviewUid(verifyUserId);
        seedDAO.updateById(update);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(id);
    }


    /**
     * 编辑菌包
     * @param seed
     * @param isAdd
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> seedSave(Seed seed,boolean isAdd) {

        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);

        //应该比较分
        if(seed.getOpenTime().getTime() < System.currentTimeMillis() ||
            seed.getCloseTime().getTime() < seed.getOpenTime().getTime() ||
                seed.getCloseTime().getTime() < System.currentTimeMillis()
        ){
            return resultData.setMessageEnum(MessageEnum.SEED_TIME_ERROR);
        }

        //校验总价与单价是否整除
        BigDecimal[] result = seed.getTotalAmount().divideAndRemainder(seed.getPerPrice());
        if(result[1].compareTo(new BigDecimal("0")) != 0 )
           return resultData.setMessageEnum(MessageEnum.SEED_PRICE_ERROR);

        if (isAdd) {
            seed.setAddTime(new Date());
            if(seedDAO.insert(seed) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(seed.getId());
        } else { //更新
            if(seedDAO.updateById(seed) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(seed.getId());
        }
        return resultData;
    }

    /**
     * 菌包详情
     * @param id
     * @return
     */
    @Override
    @DS("read")
    public Seed seed(Integer id) {
        return seedDAO.selectByPrimaryKeyWithBLOB(id);
    }


    /**
     * 菌包售罄
     * @param id
     * @deprecated
     * @return
     */
    @Override
    @DS("read")
    public Integer seedFull(Integer id) {
        return -1;
    }


    /**
     * 菌包是否上架
     * @param id
     * @return
     */
    @Override
    @DS("write")
    public Boolean seedShow(Integer id) {
        Seed seed = seedDAO.selectById(id);
        if(null == seed)
            return false;
        Seed update = new Seed();
        update.setId(seed.getId());
        update.setIsShow(!seed.getIsShow());
        seedDAO.updateById(update);
        return update.getIsShow();
    }


    @Override
    @DS("read")
    public PageResult<HomeSeed> seedListForHome(Integer page, Integer size) {
        IPage<HomeSeed> pageResult = seedDAO.selectSeedListForHome(new Page<>(page, size));
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("read")
    public ResultData<Integer> seedCancel(Integer id) {
        return null;
    }

    @Override
    @DS("read")
    public SeedDetail seedDetail(Integer id) {
        return seedDAO.seedDetail(id);
    }

    @Override
    @DS("read")
    public List<HomeSeed> recommendSeed() {
        return seedDAO.recommendSeed();
    }

    @Override
    @DS("read")
    public HomeSeed selectOneSaleDownSeed() {
        return seedDAO.selectOneFullSeed();
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<Seed> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("菌包售罄列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(seedDAO, search, Seed.class, downloadInfo)).start();
    }
}

