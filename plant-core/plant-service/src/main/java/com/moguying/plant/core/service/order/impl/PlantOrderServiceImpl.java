package com.moguying.plant.core.service.order.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.*;
import com.moguying.plant.core.annotation.FarmerTrigger;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.block.BlockDAO;
import com.moguying.plant.core.dao.mall.MallOrderDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapExcLogDAO;
import com.moguying.plant.core.dao.reap.ReapWeighDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.coin.vo.ExcReap;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import com.moguying.plant.core.entity.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.ReapExcLog;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.SeedOrder;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.entity.seed.vo.*;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.scheduled.CloseOrderScheduled;
import com.moguying.plant.core.scheduled.task.CloseSeedPayOrder;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.payment.PaymentApiService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.core.service.user.UserInviteService;
import com.moguying.plant.core.service.user.UserMessageService;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.InterestUtil;
import com.moguying.plant.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PlantOrderServiceImpl implements PlantOrderService {


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

    @Autowired
    private ReapWeighDAO reapWeighDAO;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private MallProductDAO mallProductDAO;


    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private MallOrderDAO mallOrderDAO;


    @Value("${reap.exchange.weigh}")
    private BigDecimal exWeigh;


    @Autowired
    private ReapExcLogDAO reapExcLogDAO;


    @Override
    @DS("write")
    public ResultData<BuyOrderResponse> plantOrder(BuyOrder order, Integer userId) {
        return plantOrder(order,userId,false);
    }

    /**
     * 提交菌包订单
     *
     * @param order
     * @return
     */
    @Override
    @DS("write")
    @Transactional
    public ResultData<BuyOrderResponse> plantOrder(BuyOrder order, Integer userId,boolean isTaste) {
        ResultData<BuyOrderResponse> result = new ResultData<>(MessageEnum.ERROR, null);

        // 用户不存在
        User user = userDAO.selectById(userId);
        if (null == user)
            return result.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        // 菌包是否存在，是否在售，是否上架
        Seed seed = seedDAO.seedInfoWithTypeById(order.getSeedId());
        if (null == seed || !seed.getState().equals(SeedEnum.REVIEWED.getState()) || !seed.getIsShow())
            return result.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);

        if(!isTaste && seed.getTypeInfo().getIsForNew())
            return result.setMessageEnum(MessageEnum.SEED_ONLY_FOR_NEWER);


        // 是否在售买时间
        if (seed.getOpenTime().getTime() > System.currentTimeMillis() || seed.getCloseTime().getTime() < System.currentTimeMillis())
            return result.setMessageEnum(MessageEnum.SEED_NOT_IN_TIME);

        // 是否售罄
        if (seed.getLeftCount() == 0)
            return result.setMessageEnum(MessageEnum.SEED_IS_FULL);

        // 是否超出购买份数
        order.setCount(Math.min(seed.getLeftCount(), order.getCount()));

        // 购买总价
        BigDecimal buyAmount = BigDecimal.ZERO;
        if(!isTaste)
            buyAmount = InterestUtil.INSTANCE.calAmount(order.getCount(), seed.getPerPrice());

        // 减少库存
        if (seedDAO.decrSeedLeftCount(order.getCount(), order.getSeedId()) <= 0)
            return result.setMessageEnum(MessageEnum.SEED_LEFT_COUNT_NOT_ENOUGH);

        // 生成订单
        SeedOrderDetail seedOrderDetail = new SeedOrderDetail();
        seedOrderDetail.setUserId(userId);
        seedOrderDetail.setSeedId(order.getSeedId());
        seedOrderDetail.setBuyCount(order.getCount());
        seedOrderDetail.setBuyAmount(buyAmount);
        // 体验订单
        if (isTaste) {
            seedOrderDetail.setOrderNumber(OrderPrefixEnum.TI_YAN_BUY.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        } else {
            seedOrderDetail.setOrderNumber(OrderPrefixEnum.SEED_ORDER_DETAIL.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        }
        seedOrderDetail.setAddTime(new Date());

        if (seedOrderDetailDAO.insert(seedOrderDetail) > 0) {
            BuyOrderResponse orderResponse = new BuyOrderResponse();
            orderResponse.setSeedName(seed.getName());
            orderResponse.setBuyAmount(buyAmount);
            orderResponse.setBuyCount(order.getCount());
            orderResponse.setGrowDays(seed.getGrowDays());
            orderResponse.setPerPrice(seed.getPerPrice());
            orderResponse.setOrderId(seedOrderDetail.getId());
            orderResponse.setOrderNumber(seedOrderDetail.getOrderNumber());
            orderResponse.setPerWeigh(seed.getTypeInfo().getPerWeigh());
            // 非体验需加入关单队列
            if(!isTaste) {
                UserMoney userMoney = moneyDAO.selectById(userId);
                orderResponse.setAvailableMoney(userMoney.getAvailableMoney());
                // 默认订单180秒关单
                orderResponse.setLeftSecond(expireTime.intValue());

                // 添加至关单队列
                closeOrderScheduled.addCloseItem(new CloseSeedPayOrder(seedOrderDetail.getId(), expireTime));
            }
            result.setMessageEnum(MessageEnum.SUCCESS).setData(orderResponse);
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
    @DS("write")
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
    @DS("write")
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

    @Override
    @FarmerTrigger(action = "plant")
    @TriggerEvent(action = "plant")
    @DS("write")
    public ResultData<TriggerEventResult<PlantOrderResponse>> plantSeed(Integer userId, PlantOrder plantOrder) {
        return plantSeed(userId,plantOrder,false);
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
    @DS("write")
    public ResultData<TriggerEventResult<PlantOrderResponse>> plantSeed(Integer userId, PlantOrder plantOrder,boolean isTaste) {
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 是否设置了支付密码
        User user = userDAO.userInfoById(userId);
        // 非体验时校验
        if(!isTaste) {
            if (null == user || StringUtils.isEmpty(user.getPayPassword()))
                return resultData.setMessageEnum(MessageEnum.NEED_PAY_PASSWORD);

            // 支付密码是否正确
            String payPassword = PasswordUtil.INSTANCE.encode(plantOrder.getPayPassword().getBytes());
            if (!payPassword.equals(user.getPayPassword()))
                return resultData.setMessageEnum(MessageEnum.PAY_PASSWORD_ERROR);
        }
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
        Date plantTime;
        Date endTime;
        if (isTaste) {
            //体验种植为当天可采
            plantTime = new Date();
            endTime = DateUtil.INSTANCE.todayEnd();
        } else {
            plantTime = DateUtil.INSTANCE.dateBegin(new Date(), seedType.getPlantType() - 1);
            endTime = DateUtil.INSTANCE.dateEnd(new Date(), seedType.getGrowDays());
        }

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
        if(!isTaste) {
            reap.setPreAmount(InterestUtil.INSTANCE.calAmount(plantOrder.getPlantCount(), seedType.getPerPrice()));
            reap.setPreProfit(InterestUtil.INSTANCE.calInterest(reap.getPreAmount(), seedType.getInterestRates(), seedType.getGrowDays()));
        }
        reap.setPlantTime(plantTime);
        reap.setPreReapTime(endTime);
        reap.setAddTime(new Date());
        reap.setReapTimes(1);
        BigDecimal plantWeigh = seedType.getPerWeigh().multiply(new BigDecimal(plantOrder.getPlantCount()));
        reap.setPlantWeigh(plantWeigh);
        if (reapDAO.insert(reap) <= 0)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_INFO_CREATE_FAIL);


        //非体验时使用
        if(!isTaste) {
            //更新产量
            reapWeighDAO.incField(new ReapWeigh(userId).setTotalWeigh(plantWeigh));
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
        response.setSeedTypeName(seedType.getClassName());
        response.setGrowDays(seedType.getGrowDays());
        response.setPerWeigh(seedType.getPerWeigh());
        response.setReapId(reap.getId());

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
     * @param excReap
     * @return
     */
    @Override
    @DS("write")
    @Transactional
    public ResultData<Integer> plantReapExchange(Integer userId,ExcReap excReap) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        //兑换单个采摘或兑换剩余产量，但不能同时
        if(null != excReap.getReapId() && null != excReap.getExcWeigh())
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        UserAddress userAddress = userAddressDAO.selectById(excReap.getAddressId());
        Optional<UserAddress> addressOptional = Optional.ofNullable(userAddress);
        if(!addressOptional.isPresent())
            return resultData.setMessageEnum(MessageEnum.USER_ADDRESS_NO_EXISTS);
        SeedType seedType = null;
        BigDecimal excWeigh = BigDecimal.ZERO;
        BigDecimal excProfit = BigDecimal.ZERO;
        if(null != excReap.getReapId()) {
            Reap reap = reapDAO.selectById(excReap.getReapId());
            Optional<Reap> reapOptional = Optional.ofNullable(reap);
            if(!reapOptional.isPresent())
                return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_EXISTS);
            if(reap.getPlantWeigh().compareTo(exWeigh) < 0)
                return resultData.setMessageEnum(MessageEnum.REAP_EXCHANGE_WEIGH_ERROR);
            ResultData<Integer> excReapItem = excReapItem(reap);
            if(!excReapItem.getMessageEnum().equals(MessageEnum.SUCCESS))
                return resultData;
            //添加商城订单
            seedType = seedTypeDAO.selectById(reap.getSeedType());
            excWeigh = reap.getPlantWeigh();
            excProfit = reap.getPreAmount().add(reap.getPreProfit());
        } else if( null != excReap.getExcWeigh()){
            seedType = seedTypeDAO.selectOne(new QueryWrapper<SeedType>().lambda().eq(SeedType::getExMallDefault,true));
            ReapWeigh reapWeigh = reapWeighDAO.selectOne(new QueryWrapper<ReapWeigh>().lambda().eq(ReapWeigh::getUserId, userId));
            if(reapWeigh.getAvailableWeigh().compareTo(exWeigh) < 0)
                return resultData.setMessageEnum(MessageEnum.REAP_EXCHANGE_WEIGH_ERROR);
            excWeigh = reapWeigh.getAvailableWeigh();
            if(excWeigh.compareTo(excReap.getExcWeigh()) != 0)
                return resultData;
        }

        Optional<SeedType> seedTypeOptional = Optional.ofNullable(seedType);
        if(!seedTypeOptional.isPresent())
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_EXIST);
        Optional<Integer> integer = seedTypeOptional.map(SeedType::getExMallProduct);
        MallProduct mallProduct = mallProductDAO.selectById(integer.orElse(0));
        if(null == mallProduct)
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);

        BigDecimal[] bigDecimals = excWeigh.divideAndRemainder(seedType.getExMallWeigh());
        if(bigDecimals[0].intValue() <= 0)
            return resultData.setMessageEnum(MessageEnum.REAP_WEIGH_NOT_ENOUGH);

        BuyProduct buyProduct = new BuyProduct();
        buyProduct.setProductId(mallProduct.getId());
        buyProduct.setBuyCount(bigDecimals[0].intValue());
        SubmitOrder submitOrder = new SubmitOrder().setAddressId(excReap.getAddressId()).setMark("成品兑换实物")
                .setProducts(Collections.singletonList(buyProduct));
        ResultData<BuyResponse> buyResponseResultData = mallProductService.submitOrder(submitOrder, userId);
        if(buyResponseResultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            MallOrder mallOrder = new MallOrder();
            mallOrder.setId(buyResponseResultData.getData().getOrderId());
            mallOrder.setPayTime(new Date());
            mallOrder.setState(MallEnum.ORDER_HAS_PAY.getState());
            mallOrder.setAccountPayAmount(BigDecimal.ZERO);
            mallOrder.setCarPayAmount(BigDecimal.ZERO);
            if(mallOrderDAO.updateById(mallOrder) <= 0) return resultData;
            ReapExcLog log = new ReapExcLog();
            if(null != excReap.getReapId()) {
                log.setReapId(excReap.getReapId());
                reapWeighDAO.incField(new ReapWeigh(userId).setAvailableWeigh(bigDecimals[1]));
                reapWeighDAO.decField(new ReapWeigh(userId).setAvailableProfit(excProfit));
            } else {
                reapWeighDAO.decField(new ReapWeigh(userId).setAvailableWeigh(excWeigh.subtract(bigDecimals[1])));
            }
            reapWeighDAO.incField(new ReapWeigh(userId).setHasExWeigh(excWeigh.subtract(bigDecimals[1])));

            log.setUserId(userId);
            log.setProductId(mallProduct.getId());
            log.setExcCount(bigDecimals[0].intValue());
            log.setExcWeigh(excWeigh.subtract(bigDecimals[1]));
            log.setAddTime(new Date());
            reapExcLogDAO.insert(log);

            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(bigDecimals[0].intValue());
        }
        return resultData;
    }


    private ResultData<Integer> excReapItem(Reap reap){
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        //置reap状态
        Reap update = new Reap();
        update.setId(reap.getId());
        update.setState(ReapEnum.EXCHANGE_DONE.getState());
        if(reapDAO.updateById(update) > 0){
            //更新用户本金
            UserMoneyOperator collectCapitalOperator = new UserMoneyOperator();
            collectCapitalOperator.setOpType(MoneyOpEnum.EXCHANGE_REAP);
            collectCapitalOperator.setOperationId(reap.getOrderNumber());
            UserMoney collectCapital = new UserMoney(reap.getUserId());
            collectCapital.setCollectCapital(reap.getPreAmount().negate());
            collectCapitalOperator.setUserMoney(collectCapital);
            if (userMoneyService.updateAccount(collectCapitalOperator) == null)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            //更新用户收益
            UserMoney collectProfit = new UserMoney(reap.getUserId());
            UserMoneyOperator collectProfitOperator = new UserMoneyOperator();
            collectProfitOperator.setOperationId(reap.getOrderNumber());
            collectProfitOperator.setOpType(MoneyOpEnum.EXCHANGE_REAP);
            collectProfit.setCollectInterest(reap.getPreProfit().negate());
            collectProfitOperator.setUserMoney(collectProfit);
            if (userMoneyService.updateAccount(collectProfitOperator) == null)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DS("write")
    public PaymentRequest<WebHtmlPayRequest> webHtmlPayRequestData(SeedOrderDetail seedOrderDetail) {
        return paymentService.generateWebHtmlPayData(seedOrderDetail);
    }


    @Override
    @Transactional
    @DS("write")
    public ResultData<Integer> payOrderSuccess(SeedOrderDetail orderDetail, User userInfo) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        SeedOrderDetail update = new SeedOrderDetail();
        update.setId(orderDetail.getId());
        update.setPayTime(new Date());
        update.setState(SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());
        // 更新seedDetail
        if (seedOrderDetailDAO.updateById(update) <= 0)
            return resultData;

        Integer orderId = seedOrderService.incrSeedOrder(orderDetail);
        if (orderId <= 0) {
            return resultData;
        }
        // 成功购买菌包数量
        Integer successCount =
                seedOrderDetailDAO.sumOrderCountBySeedId(orderDetail.getSeedId(), SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());
        Seed seed = seedDAO.selectById(orderDetail.getSeedId());
        // 是否已售罄
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
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(orderId);
    }
}
