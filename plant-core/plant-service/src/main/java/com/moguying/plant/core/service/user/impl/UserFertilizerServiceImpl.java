package com.moguying.plant.core.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.FertilizerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.fertilizer.FertilizerTypeDAO;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerDot;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerSearch;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.entity.user.vo.UserFertilizerInfo;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserFertilizerServiceImpl implements UserFertilizerService {

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private FertilizerTypeDAO fertilizerTypeDAO;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;


    @Autowired
    private StringRedisTemplate dotRedisTemplate;

    @Value("${excel.download.dir}")
    private String downloadDir;


    @Override
    @DS("write")
    public PageResult<UserFertilizerInfo> userFertilizers(Integer page, Integer size, Integer userId, FertilizerSearch search) {
        FertilizerUseCondition condition = new FertilizerUseCondition();
        condition.setUserId(userId);
        if (null != search.getType())
            condition.setType(search.getType());
        IPage<UserFertilizerInfo> pageResult = userFertilizerDAO.userFertilizers(new Page<>(page, size), condition);
        //查询即置过期
        userFertilizerDAO.updateOutTimeFertilizer(userId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public List<UserFertilizerInfo> canUseFertilizers(FertilizerUseCondition condition) {

        // 指定菌包
        if (null != condition.getSeedOrderId()) {
            //查询匹配对应菌包类型
            SeedOrderDetail orderDetail =
                    seedOrderDetailDAO.selectByIdAndUserIdWithSeedTypeInfo(condition.getSeedOrderId(), condition.getUserId());
            condition.setSeedTypeId(orderDetail.getSeedTypeId());
            condition.setType(FertilizerEnum.FULL_FERTILIZER.getState());
        }


        // 指定商品(该订单只有一个商品时才有效)
        if (Objects.nonNull(condition.getMallOrderId())) {
            MallOrderDetail detail = new MallOrderDetail();
            detail.setUserId(condition.getUserId());
            detail.setOrderId(condition.getMallOrderId());
            List<MallOrderDetail> details = mallOrderDetailDAO.selectList(new QueryWrapper<>(detail));
            if (Objects.nonNull(details) && details.size() == 1) {
                condition.setProductId(details.get(0).getProductId());
            }
            condition.setType(FertilizerEnum.FULL_FERTILIZER.getState());
        }

        condition.setExpireTime(new Date());
        condition.setState(FertilizerEnum.FERTILIZER_NO_USE.getState());
        // 过滤为只显示非现金红包
        List<Integer> types = new ArrayList<>();
        fertilizerTypeDAO.selectSelective(new FertilizerType())
                .stream()
                .filter((x) -> x.getId() < 4)
                .forEach((x) -> types.add(x.getId()));
        condition.setTypes(types);
        return userFertilizerDAO.userFertilizers(condition);
    }

    @DS("read")
    @Override
    public PageResult<UserFertilizer> userFertilizerList(Integer page, Integer size, UserFertilizer where) {
        IPage<UserFertilizer> pageResult = userFertilizerDAO.selectSelective(new Page(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<UserFertilizer> search, HttpServletRequest request) {
        new Thread(new DownloadService<>(userFertilizerDAO, search, UserFertilizer.class,
                new DownloadInfo("用户优惠券", request.getServletContext(), userId, downloadDir))).start();
    }

    /**
     * 手动添加用户券
     *
     * @param userFertilizer
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> addUserFertilizer(UserFertilizer userFertilizer) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        Fertilizer fertilizer = fertilizerDAO.selectById(userFertilizer.getFertilizerId());
        if (Objects.isNull(fertilizer))
            return resultData.setMessageEnum(MessageEnum.FERTILIZER_NOT_FOUND);
        return fertilizerService.distributeFertilizer(fertilizer.getTriggerGetEvent(),
                new TriggerEventResult().setUserId(userFertilizer.getUserId()), fertilizer.getId());
    }

    @Override
    @DS("read")
    public UserFertilizer getUserFertilizer(Integer userId, Integer id, Integer type) {
        if (type == null)
            return userFertilizerDAO.findByIdAndUserId(userId, id);

        if (FertilizerEnum.MONEY_FERTILIZER.getState().equals(type))
            return userFertilizerDAO.getUserFertilizer(userId, id, type);

        return null;
    }

    @Override
    @DS("write")
    public Boolean redPackageSuccess(UserFertilizer userFertilizer) {
        UserMoneyOperator operator = new UserMoneyOperator();
        operator.setOperationId(userFertilizer.getUseOrderNumber());
        operator.setOpType(MoneyOpEnum.RED_PACKAGE);
        UserMoney money = new UserMoney(userFertilizer.getUserId());
        money.setAvailableMoney(userFertilizer.getFertilizerAmount());
        operator.setUserMoney(money);
        if (userMoneyService.updateAccount(operator) == null) return false;

        // 更新券状态
        userFertilizer.setState(1);
        userFertilizer.setUseTime(new Date());
        return userFertilizerDAO.updateById(userFertilizer) > 0;
    }

    @Override
    @DS("read")
    public UserFertilizer userFertilizer(Integer userId, String orderNumber) {
        return userFertilizerDAO.getUserAndNumber(userId, orderNumber, FertilizerEnum.MONEY_FERTILIZER.getState());
    }


    @Override
    @DS("read")
    public FertilizerDot fertilizerDot(Integer userId) {
        String dotJsonString = dotRedisTemplate.opsForValue().get("fertilizer:dot:" + userId);
        if(null == dotJsonString)
            return new FertilizerDot();
        return JSON.parseObject(dotJsonString, FertilizerDot.class);
    }

    @Override
    @DS("read")
    public Boolean cancelFertilizerDot(Integer userId) {
        return dotRedisTemplate.opsForValue().setIfPresent("fertilizer:dot:" + userId,JSON.toJSONString(new FertilizerDot(false,0)));
    }
}

