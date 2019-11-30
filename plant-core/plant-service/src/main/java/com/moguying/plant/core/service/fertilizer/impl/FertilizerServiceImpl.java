package com.moguying.plant.core.service.fertilizer.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.FertilizerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.fertilizer.FertilizerTypeDAO;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.reap.SaleCoinLogDao;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerDot;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.core.entity.user.vo.UserFertilizerInfo;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FertilizerServiceImpl implements FertilizerService {

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Autowired
    private FertilizerTypeDAO fertilizerTypeDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Autowired
    private SaleCoinLogDao saleCoinLogDao;


    @Autowired
    private StringRedisTemplate dotRedisTemplate;

    @Override
    @DS("write")
    public int addFertilizer(Fertilizer add) {
        add.setAddTime(new Date());
        if (fertilizerDAO.insert(add) > 0)
            return add.getId();
        return 0;
    }

    @Override
    public Integer updateFertilizer(Fertilizer update) {
        return fertilizerDAO.updateById(update);
    }

    @Override
    @DS("write")
    public ResultData<Integer> deleteFertilizer(int id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (userFertilizerDAO.fertilizerIsUsing(id) > 0)
            return resultData.setMessageEnum(MessageEnum.FERTILIZER_CAN_NOT_DELETE);
        if (fertilizerDAO.deleteById(id) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    @DS("read")
    public PageResult<Fertilizer> fertilizerList(int page, int size, Fertilizer where) {
        IPage<Fertilizer> pageResult = fertilizerDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }


    @Override
    @DS("read")
    public List<FertilizerType> fertilizerType() {
        return fertilizerTypeDAO.selectList(new QueryWrapper<>());
    }


    /**
     * @param condition   使用券的条件
     * @param fertilizers 用户使用的券id
     * @param orderNumber 使用相关的流水号
     * @return
     */
    @Override
    @DS("write")
    @Transactional
    public ResultData<BigDecimal> useFertilizers(FertilizerUseCondition condition, List<Integer> fertilizers, String orderNumber) {
        ResultData<BigDecimal> resultData = new ResultData<>(MessageEnum.ERROR, null);

        if (null == condition.getUserId() || null == condition.getAmount() || null == fertilizers || fertilizers.size() == 0)
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        // 指定菌包
        if (null != condition.getSeedOrderId()) {
            SeedOrderDetail orderDetail = seedOrderDetailDAO.selectByIdAndUserIdWithSeedTypeInfo(condition.getSeedOrderId(), condition.getUserId());
            condition.setSeedTypeId(orderDetail.getSeedTypeId());
        }

       /* //由于需要将券与种植的类型匹配，而不是与棚的id相匹配
        //将前端传入的棚id转为在棚区中种植的菌包类型id
        if (null != condition.getBlockId()) {
            Block block = blockDAO.selectById(condition.getBlockId());
            condition.setBlockId(block.getSeedType());
        }*/

        // 指定商城
        if (null != condition.getMallOrderId()) {
            List<OrderItem> mallOrderDetail = mallOrderDetailDAO.selectDetailListByOrderId(condition.getMallOrderId(), condition.getUserId());
            if (mallOrderDetail.size() == 1)
                condition.setProductId(mallOrderDetail.get(0).getProductId());
            else
                condition.setProductId(0);
        }

        // 该订单可使用的券列表
        List<UserFertilizerInfo> fertilizerInfos = userFertilizerDAO.userFertilizers(condition);

        // 用户使用的券
        List<UserFertilizerInfo> userFertilizerInfos = userFertilizerDAO.selectByIds(fertilizers);
        if (!fertilizerInfos.containsAll(userFertilizerInfos))
            return resultData.setMessageEnum(MessageEnum.FERTILIZER_USER_ERROR);

        // 更新券的使用状态
        UserFertilizer update = new UserFertilizer();
        update.setState(FertilizerEnum.FERTILIZER_USED.getState());
        update.setUseOrderNumber(orderNumber);
        update.setUseTime(new Date());
        if (userFertilizerDAO.updateStateByIds(fertilizers, update) > 0) {
            BigDecimal affectAmount = BigDecimal.ZERO;
            for (UserFertilizerInfo info : userFertilizerInfos) {
                affectAmount = affectAmount.add(info.getFertilizerAmount());
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(affectAmount);
        }
        return resultData;
    }

    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> distributeFertilizer(String triggerGetEvent, Integer userId) {
        return distributeFertilizer(triggerGetEvent, new TriggerEventResult().setUserId(userId));
    }

    @Override
    @Transactional
    @DS("write")
    public ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult) {
        return distributeFertilizer(triggerGetEvent, triggerEventResult, null);
    }


    @Override
    @Transactional
    @DS("write")
    public ResultData<Integer> distributeFertilizer(String triggerGetEvent, TriggerEventResult triggerEventResult, Integer fertilizerId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);

        Fertilizer where = new Fertilizer();
        where.setTriggerGetEvent(triggerGetEvent);
        if (!Objects.isNull(fertilizerId)) where.setId(fertilizerId);
         where.setStartTime(new Date());

        // 获取可发放的所有券
        List<Fertilizer> fertilizers = fertilizerDAO.selectSelective(where);
        if (null == fertilizers || fertilizers.size() <= 0) return resultData;

        for (Fertilizer fertilizer : fertilizers) {
            if (fertilizer == null || fertilizer.getPerCount() == null || fertilizer.getPerCount() <= 0) continue;

            // 是否只能领取一次
            if (fertilizer.getIsSingleTrigger()) {
                // 有发过，则不重复发
                Integer count = userFertilizerDAO.hasFertilizer(triggerEventResult.getUserId(), fertilizer.getId());
                if (count > 0) continue;
            }

            // 每次触发发送多少张
            for (int i = 1; i <= fertilizer.getPerCount(); i++) {

                UserFertilizer userFertilizer = new UserFertilizer();
                userFertilizer.setFertilizerId(fertilizer.getId());
                userFertilizer.setUserId(triggerEventResult.getUserId());
                userFertilizer.setAddTime(new Date());
                userFertilizer.setUseOrderNumber(OrderPrefixEnum.RED_PACKAGE.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());

                if (null != triggerEventResult.getData() && triggerEventResult.getData() instanceof PlantOrderResponse) {
                    PlantOrderResponse plantOrderResponse = (PlantOrderResponse) triggerEventResult.getData();

                    // 发的券跟种植订单关联
                    userFertilizer.setUseOrderNumber(plantOrderResponse.getOrderNumber());

                    // 指定大棚
                    // if (fertilizer.getUseInBlock() == null || !fertilizer.getUseInBlock().equals(plantOrderResponse.getBlockId()))
                    // continue;

                    // 指定菌包
                    if (fertilizer.getUseInSeedType() == null || !fertilizer.getUseInSeedType().equals(plantOrderResponse.getSeedTypeId()))
                        continue;

                    // 种植时发放的券为红包
                    if (fertilizer.getFertilizerAmountIsRate()) {
                        // 红包金额为种植金额 * 红包比率
                        BigDecimal amount = plantOrderResponse.getPlantAmount()
                                .multiply(fertilizer.getFertilizerAmount())
                                .divide(new BigDecimal("100.0"), 2,BigDecimal.ROUND_DOWN);
                        userFertilizer.setFertilizerAmount(amount);
                    } else {
                        // 固定金额
                        userFertilizer.setFertilizerAmount(fertilizer.getFertilizerAmount());
                    }

                } else {
                    userFertilizer.setFertilizerAmount(fertilizer.getFertilizerAmount());
                }

                // 设置券有效期
                if (null != fertilizer.getExpireDays() && fertilizer.getExpireDays() > 0) {
                    userFertilizer.setStartTime(DateUtil.INSTANCE.todayBegin());
                    userFertilizer.setEndTime(DateUtils.addDays(DateUtil.INSTANCE.todayEnd(), fertilizer.getExpireDays()));
                } else {
                    userFertilizer.setStartTime(fertilizer.getStartTime());
                    userFertilizer.setEndTime(fertilizer.getExpireTime());
                }

                // 新增券
                if (userFertilizer.getFertilizerAmount().compareTo(BigDecimal.ZERO) > 0 && userFertilizerDAO.insert(userFertilizer) > 0) {
                    resultData.setMessageEnum(MessageEnum.SUCCESS);
                    dotRedisTemplate.opsForValue().setIfAbsent("fertilizer:dot:"+userFertilizer.getUserId(), JSON.toJSONString(new FertilizerDot(true,fertilizer.getTypeId())));
                } else {
                    return resultData.setMessageEnum(MessageEnum.ERROR);
                }
            }
        }
        return resultData;
    }


    @Override
    @DS("write")
    public boolean useDoneFertilizers(String orderNumber) {
        UserFertilizer where = new UserFertilizer();
        where.setUseOrderNumber(orderNumber);
        if (userFertilizerDAO.selectSelective(where).size() > 0) {
            return userFertilizerDAO.updateStateByOrderNumber(orderNumber, FertilizerEnum.FERTILIZER_USED.getState()) > 0;
        } else
            return true;
    }

    @Override
    @DS("read")
    public PageResult<ExchangeInfo> showFertilizer(Integer page, Integer size) {
        IPage<ExchangeInfo> pageResult = fertilizerDAO.showFertilizer(new Page<>(page, size));
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public Fertilizer findById(Integer fertilizerId) {
        return fertilizerDAO.selectById(fertilizerId);
    }

    @Override
    @DS("read")
    public PageResult<ExchangeInfo> showFertilizerLog(Integer page, Integer size, Integer userId) {
        IPage<ExchangeInfo> pageResult = saleCoinLogDao.showFertilizerLog(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }
}
