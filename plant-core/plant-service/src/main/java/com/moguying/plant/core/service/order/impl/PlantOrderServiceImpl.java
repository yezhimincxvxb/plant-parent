package com.moguying.plant.core.service.order.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.FarmerTrigger;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.constant.*;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.dto.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.dto.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.vo.*;
import com.moguying.plant.core.scheduled.CloseOrderScheduled;
import com.moguying.plant.core.scheduled.task.CloseSeedPayOrder;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.payment.PaymentApiService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.core.service.seed.SeedService;
import com.moguying.plant.core.service.user.UserInviteService;
import com.moguying.plant.core.service.user.UserMessageService;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.InterestUtil;
import com.moguying.plant.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class PlantOrderServiceImpl implements PlantOrderService {

    Logger log = LoggerFactory.getLogger(PlantOrderServiceImpl.class);

    @Value("${order.expire.time}")
    private Long expireTime;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserMoneyDAO moneyDAO;

    @Autowired
    private SeedDAO seedDAO;

    @Autowired
    private SeedOrderService seedOrderService;

    @Autowired
    private SeedOrderDAO seedOrderDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private SeedService seedService;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private BlockDAO blockDAO;

    @Autowired
    private UserInviteService userInviteService;

    @Autowired
    private PaymentApiService paymentApiService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private CloseOrderScheduled closeOrderScheduled;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 提交菌包订单
     *
     * @param order
     * @return
     */
    @Override
    @DataSource("write")
    public ResultData<BuyOrderResponse> plantOrder(BuyOrder order, Integer userId) {
        User user = userDAO.selectById(userId);
        ResultData<BuyOrderResponse> result = new ResultData<>(MessageEnum.ERROR, null);
        if (null == user)
            return result.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        Seed seed = seedDAO.selectById(order.getSeedId());
        //菌包是否存在，是否在售
        if (null == seed || !seed.getState().equals(SeedEnum.REVIEWED.getState()) || !seed.getIsShow())
            return result.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);
        //是否在售买时间
        if (seed.getOpenTime().getTime() > System.currentTimeMillis() || seed.getCloseTime().getTime() < System.currentTimeMillis())
            return result.setMessageEnum(MessageEnum.SEED_NOT_IN_TIME);
        //是否售罄
        if (seed.getLeftCount() == 0)
            return result.setMessageEnum(MessageEnum.SEED_IS_FULL);
        //是否超出购买份数
        order.setCount(Math.min(seed.getLeftCount(), order.getCount()));

        BigDecimal buyAmount = InterestUtil.INSTANCE.calAmount(order.getCount(), seed.getPerPrice());

        //减少库存
        if (seedDAO.decrSeedLeftCount(order.getCount(), order.getSeedId()) <= 0) {
            return result.setMessageEnum(MessageEnum.SEED_LEFT_COUNT_NOT_ENOUGH);
        }

        //生成订单
        SeedOrderDetail seedOrderDetail = new SeedOrderDetail();
        seedOrderDetail.setUserId(userId);
        seedOrderDetail.setSeedId(order.getSeedId());
        seedOrderDetail.setBuyCount(order.getCount());
        seedOrderDetail.setBuyAmount(buyAmount);
        seedOrderDetail.setOrderNumber(OrderPrefixEnum.SEED_ORDER_DETAIL.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        seedOrderDetail.setAddTime(new Date());

        if (seedOrderDetailDAO.insert(seedOrderDetail) > 0) {
            BuyOrderResponse orderResponse = new BuyOrderResponse();
            orderResponse.setSeedName(seed.getName());
            orderResponse.setBuyAmount(buyAmount);
            orderResponse.setBuyCount(order.getCount());
            orderResponse.setGrowDays(seed.getGrowDays());
            orderResponse.setPerPrice(seed.getPerPrice());
            orderResponse.setOrderId(seedOrderDetail.getId());
            UserMoney userMoney = moneyDAO.selectById(userId);
            orderResponse.setAvailableMoney(userMoney.getAvailableMoney());
            //默认订单180秒关单
            orderResponse.setLeftSecond(expireTime.intValue());
            orderResponse.setOrderNumber(seedOrderDetail.getOrderNumber());
            result.setMessageEnum(MessageEnum.SUCCESS).setData(orderResponse);
            //添加至关单队列
            closeOrderScheduled.addCloseItem(new CloseSeedPayOrder(seedOrderDetail.getId(), expireTime));
        }

        return result;
    }


    /**
     * 提交支付订单
     *
     * @param payOrder
     * @param userId
     * @return
     */
    @Override
    @DataSource("write")
    public ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId) {
        return paymentService.checkPayOrder(payOrder, userId, seedOrderDetailDAO, SeedOrderDetail.class);
    }


    /**
     * 确认支付
     *
     * @param payOrder
     * @param userId
     * @return
     */
    @Override
    @DataSource("write")
    public ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, Integer userId) {
        SeedOrderDetail orderDetail = seedOrderDetailDAO.selectById(payOrder.getOrderId());
        ResultData<PaymentResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (null == orderDetail || null != orderDetail.getPayTime())
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_DETAIL_HAS_PAY);
        User userInfo = userDAO.selectById(userId);
        resultData = paymentApiService.payOrder(payOrder, orderDetail, userInfo);
        if (!resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (resultData.getMessageEnum().equals(MessageEnum.ERROR)) {
                //由于第三方支付流水单不能重复，提交支付失败后将关闭订单
                seedOrderDetailService.seedOrderCancel(orderDetail.getId(), userId);
            }
            return resultData;
        }

        //增加菌包数
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            if (!payOrderSuccess(orderDetail, userInfo).getMessageEnum().equals(MessageEnum.SUCCESS)) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData.setMessageEnum(MessageEnum.ERROR);
            }
        }
        return resultData;

    }

    /**
     * 种植菌包
     *
     * @param plantOrder
     * @return
     */
    @FarmerTrigger(action = "plant")
    @TriggerEvent(action = "plant")
    @Override
    @DataSource("write")
    public ResultData<TriggerEventResult<PlantOrderResponse>> plantSeed(Integer userId, PlantOrder plantOrder) {
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 是否设置了支付密码
        User user = userDAO.userInfoById(userId);
        if (null == user || StringUtils.isEmpty(user.getPayPassword()))
            return resultData.setMessageEnum(MessageEnum.NEED_PAY_PASSWORD);

        // 支付密码是否正确
        String payPassword = PasswordUtil.INSTANCE.encode(plantOrder.getPayPassword().getBytes());
        if (!payPassword.equals(user.getPayPassword()))
            return resultData.setMessageEnum(MessageEnum.PAY_PASSWORD_ERROR);

        // 菌包订单是否存在
        SeedOrder seedOrder = seedOrderDAO.selectByIdAndUserId(plantOrder.getOrderId(), userId);
        if (seedOrder == null)
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_NOT_EXISTS);

        // 种植份数不能小于等于0
        if (plantOrder.getPlantCount() <= 0)
            return resultData.setMessageEnum(MessageEnum.PLANT_COUNT_ZERO);

        // 可种植的份数小于要种植的份数，则设置要种植份数为可种植份数
        int leftCount = seedOrder.getBuyCount() - seedOrder.getPlantCount();
        if (leftCount < plantOrder.getPlantCount())
            plantOrder.setPlantCount(leftCount);

        // 大棚是否开放
        Block block = blockDAO.selectById(plantOrder.getBlockId());
        if (block == null || !block.getState().equals(BlockStateEnum.OPEN.getState()) || !block.getIsShow())
            return resultData.setMessageEnum(MessageEnum.BLOCK_NOT_EXISTS);

        // 大棚已满
        if (block.getLeftCount() == 0)
            return resultData.setMessageEnum(MessageEnum.BLOCK_IS_FULL);

        // 份数限制
        if (plantOrder.getPlantCount() < block.getMinPlant() || plantOrder.getPlantCount() > block.getMaxPlant())
            return resultData.setMessageEnum(MessageEnum.PLANT_COUNT_ERROR);

        // 菌包类型不匹配
        if (!seedOrder.getSeedType().equals(block.getSeedType()))
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_PLANT_BLOCK_TYPE_ERROR);

        //  种植份数大于棚区剩余时，则修改种植份数为大棚可种数
        if (block.getLeftCount() < plantOrder.getPlantCount()) {
            plantOrder.setPlantCount(block.getLeftCount());
        }

        // 种植周期
        SeedType seedType = seedTypeDAO.selectById(seedOrder.getSeedType());
        Date plantTime = DateUtil.INSTANCE.dateBegin(new Date(), seedType.getPlantType() - 1);
        Date endTime = DateUtil.INSTANCE.dateEnd(new Date(), seedType.getGrowDays());

        // 更新订单信息
        SeedOrder updateSeedOrder = new SeedOrder();
        updateSeedOrder.setId(seedOrder.getId());
        updateSeedOrder.setPlantCount(seedOrder.getPlantCount() + plantOrder.getPlantCount());
        if (seedOrderDAO.updateById(updateSeedOrder) <= 0)
            return resultData.setMessageEnum(MessageEnum.SEED_ORDER_UPDATE_FAIL);

        // 生成采摘信息
        Reap reap = new Reap();
        reap.setOrderId(seedOrder.getId());
        reap.setSeedType(seedType.getId());
        reap.setOrderNumber(OrderPrefixEnum.PLANT_ORDER.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        reap.setUserId(userId);
        reap.setBlockId(plantOrder.getBlockId());
        reap.setPlantCount(plantOrder.getPlantCount());
        reap.setPreAmount(InterestUtil.INSTANCE.calAmount(plantOrder.getPlantCount(), seedType.getPerPrice()));
        reap.setPreProfit(InterestUtil.INSTANCE.calInterest(reap.getPreAmount(), seedType.getInterestRates(), seedType.getGrowDays()));
        reap.setPlantTime(plantTime);
        reap.setPreReapTime(endTime);
        reap.setAddTime(new Date());
        reap.setReapTimes(1);
        if (reapDAO.insert(reap) <= 0)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_INFO_CREATE_FAIL);

        // 更新预期本金
        UserMoneyOperator operator = new UserMoneyOperator();
        operator.setOperationId(reap.getOrderNumber());
        operator.setOpType(MoneyOpEnum.PLANTED_SEED);
        UserMoney money = new UserMoney(userId);
        money.setCollectCapital(reap.getPreAmount());
        operator.setUserMoney(money);
        if (userMoneyService.updateAccount(operator) == null)
            return resultData.setMessageEnum(MessageEnum.USER_MONEY_UPDATE_FAIL);

        // 更新预期收益
        money = new UserMoney(userId);
        money.setCollectInterest(reap.getPreProfit());
        operator.setUserMoney(money);
        if (userMoneyService.updateAccount(operator) == null)
            return resultData.setMessageEnum(MessageEnum.USER_MONEY_UPDATE_FAIL);

        // 是否有邀请人
        if (null != user.getInviteUid() && !user.getInviteUid().equals(0)) {
            User inviteUser = userDAO.selectById(user.getInviteUid());
            // 给邀请人发奖、通知
            if (null != inviteUser) {
                userInviteService.inviterPlanted(user, reap, inviteUser);
            }
        }

        // 更新棚区信息
        Block updateBlock = new Block();
        updateBlock.setId(plantOrder.getBlockId());
        updateBlock.setHasCount(block.getHasCount() + plantOrder.getPlantCount());
        // 棚区可种植数量等于种植份数，则设置该棚区为未开放状态
        if (block.getLeftCount().equals(plantOrder.getPlantCount()))
            updateBlock.setState(BlockStateEnum.NO_OPEN.getState());
        if (blockDAO.updateById(updateBlock) <= 0)
            return resultData.setMessageEnum(MessageEnum.BLOCK_UPDATE_FAIL);

        // 是否有使用券
        if (null != plantOrder.getFertilizerIds() && plantOrder.getFertilizerIds().size() > 0) {
            FertilizerUseCondition condition = new FertilizerUseCondition();
            condition.setUserId(userId);
            // 种植棚区
            condition.setBlockId(plantOrder.getBlockId());
            // 种植金额
            condition.setAmount(reap.getPreAmount());
            ResultData<BigDecimal> addAmount =
                    fertilizerService.useFertilizers(condition, plantOrder.getFertilizerIds(), reap.getOrderNumber());
            if (addAmount.getData().compareTo(BigDecimal.ZERO) > 0) {
                // 更新种植优惠金额
                UserMoneyOperator addMoneyOperator = new UserMoneyOperator();
                UserMoney addMoney = new UserMoney(seedOrder.getUserId());
                addMoney.setAvailableMoney(addAmount.getData());
                addMoneyOperator.setOperationId(reap.getOrderNumber());
                addMoneyOperator.setOpType(MoneyOpEnum.PANT_SEED_FERTILIZER);
                addMoneyOperator.setUserMoney(addMoney);
                if (userMoneyService.updateAccount(addMoneyOperator) == null || !fertilizerService.useDoneFertilizers(reap.getOrderNumber())) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultData.setMessageEnum(MessageEnum.USER_MONEY_UPDATE_FAIL);
                }
            }
        }

        PlantOrderResponse response = new PlantOrderResponse();
        response.setBlockNumber(block.getNumber());
        response.setBlockId(block.getId());
        response.setPlantCount(plantOrder.getPlantCount());
        response.setPreReapTime(reap.getPreReapTime());
        response.setUserId(userId);
        response.setPlantAmount(reap.getPreAmount());
        response.setSeedTypeId(seedType.getId());

        // 发送站内信
        InnerMessage message = new InnerMessage();
        message.setUserId(userId);
        message.setPhone(user.getPhone());
        message.setBlockNumber(block.getNumber());
        userMessageService.addMessage(message, SystemEnum.PHONE_MESSAGE_SEED_PLANTED_TYPE.getStateName());

        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(new TriggerEventResult<PlantOrderResponse>().setUserId(userId).setData(response));
    }


    /**
     * 采摘兑换实物
     *
     * @param reapId
     * @param excReap
     * @return
     */
    @Override
    @DataSource("write")
    public ResultData<Integer> plantReapExchange(Integer reapId, ExcReap excReap) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        //生成兑换订单记录

        //是否添加过收货地址

        //更新资金

        return null;
    }

    @Override
    @DataSource("write")
    public PaymentRequest<WebHtmlPayRequest> webHtmlPayRequestData(SeedOrderDetail seedOrderDetail) {
        return paymentService.generateWebHtmlPayData(seedOrderDetail);
    }


    @Override
    @Transactional
    @DataSource("write")
    public ResultData<Integer> payOrderSuccess(SeedOrderDetail orderDetail, User userInfo) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        SeedOrderDetail update = new SeedOrderDetail();
        update.setId(orderDetail.getId());
        update.setPayTime(new Date());
        update.setState(SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());
        //更新seedDetail
        if (seedOrderDetailDAO.updateById(update) <= 0)
            return resultData;

        if (!seedOrderService.incrSeedOrder(orderDetail)) {
            return resultData;
        }
        //成功购买菌包数量
        Integer successCount =
                seedOrderDetailDAO.sumOrderCountBySeedId(orderDetail.getSeedId(), SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());
        Seed seed = seedDAO.selectById(orderDetail.getSeedId());
        //是否已售罄
        if (seed.getTotalCount().equals(successCount)) {
            Seed seedHasFull = new Seed();
            seedHasFull.setId(seed.getId());
            seedHasFull.setFullTime(new Date());
            seedHasFull.setState(SeedEnum.SEED_IS_FULL.getState());
            if (seedDAO.updateById(seedHasFull) <= 0) {
                return resultData;
            }
        }
        //更新使用券的状态
        if (!fertilizerService.useDoneFertilizers(orderDetail.getOrderNumber()))
            return resultData;

        //发送站内信,后续应放消息队列处理
        InnerMessage message = new InnerMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(new Date());
        message.setUserId(userInfo.getId());
        message.setPhone(userInfo.getPhone());
        message.setTime(time);
        message.setCount(orderDetail.getBuyCount() + "");
        if (userMessageService.addMessage(message, SystemEnum.PHONE_MESSAGE_SEED_BUY_TYPE.getStateName()) <= 0) {
            return resultData;
        }
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }
}
