package com.moguying.plant.core.service.bargain.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.bargain.BargainDetailDao;
import com.moguying.plant.core.dao.bargain.BargainLogDao;
import com.moguying.plant.core.dao.bargain.BargainRateDao;
import com.moguying.plant.core.dao.mall.MallOrderDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.BargainRate;
import com.moguying.plant.core.entity.bargain.vo.BackBargainDetailVo;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.bargain.vo.ShareVo;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.scheduled.CloseOrderScheduled;
import com.moguying.plant.core.scheduled.task.CloseBargainOrder;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BargainDetailServiceImpl implements BargainDetailService {

    @Autowired
    private BargainDetailDao bargainDetailDao;

    @Autowired
    private BargainLogDao bargainLogDao;

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private MallOrderDAO mallOrderDAO;

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BargainRateDao bargainRateDao;

    @Autowired
    private CloseOrderScheduled closeOrderScheduled;

    @Value("${bargain.expire.time}")
    private Long bargainTime;

    /**
     * 获取比率
     */
    private BigDecimal getRate(Integer val) {
        return new BigDecimal(val + new Random().nextInt(11)).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN);
    }

    @Override
    @DS("read")
    public BargainDetail getOneByClose(Integer userId, Integer productId, Boolean state) {
        return bargainDetailDao.getOneByClose(userId, productId, state);
    }

    @Override
    @DS("read")
    public BargainDetail getOneById(Integer id) {
        return bargainDetailDao.selectById(id);
    }

    @Override
    @DS("write")
    @Transactional
    public ResultData<ShareVo> shareSuccess(Integer userId, MallProduct product, BargainRate bargainRate) {
        ResultData<ShareVo> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (Objects.isNull(product)) return resultData;
        // 重复分享
        List<BargainDetail> details = bargainDetailDao.getOneByOpen(userId, product.getId(), false);
        if (details != null && !details.isEmpty()) {
            // 删除多余的订单
            if (details.size() >= 2) {
                for (int i = 1; i < details.size(); i++) {
                    bargainDetailDao.deleteById(details.get(i));
                }
            }
            // 只获取第一单
            BargainDetail detail = details.get(0);
            ShareVo shareVo = new ShareVo()
                    .setOrderId(detail.getId());
            return resultData.setMessageEnum(MessageEnum.BARGAIN_AGAIN).setData(shareVo);
        }
        // 总价、第一刀砍了多少
        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(bargainRate.getBargainNumber()));
        BigDecimal bargainAmount = totalAmount.multiply(getRate(bargainRate.getOwnRate())).setScale(2, BigDecimal.ROUND_DOWN);
        // 首次分享，生成砍价详情
        BargainDetail add = new BargainDetail()
                .setUserId(userId)
                .setProductId(product.getId())
                .setProductCount(bargainRate.getBargainNumber())
                .setReserveAmount(BigDecimal.valueOf(bargainRate.getBargainCount() - 1).multiply(BigDecimal.valueOf(0.01)))
                .setTotalAmount(totalAmount)
                .setBargainAmount(bargainAmount)
                .setLeftAmount(totalAmount.subtract(bargainAmount))
                .setTotalCount(bargainRate.getBargainCount())
                .setBargainCount(1)
                .setSymbol(RandomStringUtils.random(12, true, true))
                .setAddTime(new Date())
                .setBargainTime(new Date())
                .setCloseTime(DateUtil.INSTANCE.nextDay(new Date()));
        if (bargainDetailDao.insert(add) <= 0)
            return resultData.setMessageEnum(MessageEnum.ADD_BARGAIN_ORDER_FAIL);
        // 日志
        BargainLog log = new BargainLog();
        log.setUserId(userId);
        log.setShareId(userId);
        log.setProductId(product.getId());
        log.setDetailId(add.getId());
        log.setHelpAmount(bargainAmount);
        log.setHelpTime(new Date());
        if (bargainLogDao.insert(log) > 0) {
            ShareVo shareVo = new ShareVo()
                    .setOrderId(add.getId());
            closeOrderScheduled.addCloseItem(new CloseBargainOrder(add.getId(), bargainTime));
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(shareVo);
        }
        return resultData.setMessageEnum(MessageEnum.ADD_BARGAIN_LOG_FAIL);
    }

    @Override
    @DS("write")
    @Transactional
    public BargainLog helpSuccess(Integer userId, BargainDetail detail) {
        // 关单
        if (!DateUtil.INSTANCE.betweenTime(detail.getAddTime(), detail.getCloseTime()) || detail.getTotalCount() <= detail.getBargainCount()) {
            bargainOrderClose(detail.getId());
            return null;
        }
        // 帮砍价格
        BigDecimal helpAmount = BigDecimal.ZERO;
        if (detail.getBargainCount() + 1 < detail.getTotalCount()) {
            // 砍价系数
            BargainRate bargainRate = bargainRateDao.selectById(detail.getProductId());
            if (Objects.isNull(bargainRate)) return null;
            // 注册时间
            User user = userDAO.selectById(userId);
            if (Objects.isNull(user)) return null;
            //  注册时间在砍价详情生成之后，默认为新用户
            if (user.getRegTime().after(detail.getAddTime())) {
                helpAmount = (detail.getLeftAmount().subtract(detail.getReserveAmount()))
                        .multiply(getRate(bargainRate.getNewRate())).setScale(2, BigDecimal.ROUND_DOWN)
                        .add(BigDecimal.valueOf(0.01));
            } else {
                helpAmount = (detail.getLeftAmount().subtract(detail.getReserveAmount()))
                        .multiply(getRate(bargainRate.getOwnRate())).setScale(2, BigDecimal.ROUND_DOWN)
                        .add(BigDecimal.valueOf(0.01));
            }
        }

        // 剩余价格等于零或小于等于帮砍价格，提前生成订单
        if (detail.getLeftAmount().compareTo(BigDecimal.ZERO) == 0 || detail.getLeftAmount().compareTo(helpAmount) <= 0) {
            detail.setBargainCount(detail.getTotalCount() - 1);
        }

        // 最后一刀
        BargainDetail update = new BargainDetail();
        if (detail.getBargainCount() + 1 == detail.getTotalCount()) {
            // 订单流水号
            String orderNumber = OrderPrefixEnum.KAN_JIA.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate();
            // 生成订单
            MallOrder mallOrder = new MallOrder();
            mallOrder.setOrderNumber(orderNumber);
            mallOrder.setUserId(detail.getUserId());
            mallOrder.setState(0);
            mallOrder.setAddTime(new Date());
            if (mallOrderDAO.insert(mallOrder) <= 0) return null;
            // 生成订单详情
            MallOrderDetail orderDetail = new MallOrderDetail();
            orderDetail.setOrderId(mallOrder.getId());
            orderDetail.setUserId(detail.getUserId());
            orderDetail.setProductId(detail.getProductId());
            orderDetail.setBuyCount(detail.getProductCount());
            orderDetail.setBuyAmount(detail.getTotalAmount());
            if (mallOrderDetailDAO.insert(orderDetail) <= 0) return null;
            // 关单
            bargainOrderClose(detail.getId());
            update.setOrderNumber(orderNumber);
            // 全部砍完
            helpAmount = detail.getLeftAmount();
        }
        // 更新
         update.setId(detail.getId())
                .setReserveAmount(detail.getReserveAmount().subtract(BigDecimal.valueOf(0.01)))
                .setBargainAmount(detail.getBargainAmount().add(helpAmount))
                .setLeftAmount(detail.getLeftAmount().subtract(helpAmount))
                .setBargainCount(detail.getBargainCount() + 1)
                .setBargainTime(new Date());
        if (bargainDetailDao.updateById(update) <= 0) return null;
        // 日志
        BargainLog log = new BargainLog();
        log.setUserId(userId);
        log.setShareId(detail.getUserId());
        log.setProductId(detail.getProductId());
        log.setDetailId(detail.getId());
        log.setHelpAmount(helpAmount);
        log.setMessage(detail.getMessage() == null ? "" : detail.getMessage());
        log.setHelpTime(new Date());
        return bargainLogDao.insert(log) > 0 ? log : null;
    }

    @Override
    @DS("read")
    public PageResult<BargainVo> successLogs(Integer page, Integer size) {
        IPage<BargainVo> pageResult = bargainDetailDao.successLogs(new Page<>(page, size));
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public List<SendNumberVo> sendNumber() {
        return bargainDetailDao.sendNumber();
    }

    @Override
    @DS("read")
    public PageResult<BargainVo> doingList(Integer page, Integer size, Integer userId, Boolean isSuccess) {
        IPage<BargainVo> pageResult = bargainDetailDao.doingList(new Page<>(page, size), userId, isSuccess);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public BargainVo productInfoByOrderId(BargainVo bargainVo) {
        BargainDetail detail = new BargainDetail();
        // 根据订单id
        if (Objects.nonNull(bargainVo.getOrderId())) {
            detail = bargainDetailDao.selectById(bargainVo.getOrderId());
            if (detail == null) return null;
        }
        // 根据口令
        if (Objects.nonNull(bargainVo.getSymbol())) {
            QueryWrapper<BargainDetail> queryWrapper = new QueryWrapper<BargainDetail>().eq("symbol", bargainVo.getSymbol());
            List<BargainDetail> details = bargainDetailDao.selectList(queryWrapper);
            if (details == null || details.size() != 1) return null;
            detail = details.get(0);
        }
        MallProduct mallProduct = mallProductDAO.selectById(detail.getProductId());
        if (mallProduct == null) return null;
        BargainRate bargainRate = bargainRateDao.selectById(mallProduct.getId());
        if (bargainRate == null) return null;
        return new BargainVo()
                .setOrderId(detail.getId())
                .setProductName(mallProduct.getName())
                .setBargainAmount(detail.getBargainAmount())
                .setLeftAmount(detail.getLeftAmount())
                .setProductInfo(mallProduct.getSummaryDesc())
                .setPicUrl(mallProduct.getPicUrl())
                .setBeginTime(detail.getAddTime())
                .setEndTime(detail.getCloseTime())
                .setRate(detail.getBargainAmount().divide(detail.getTotalAmount(), 2, BigDecimal.ROUND_DOWN))
                .setProductId(mallProduct.getId())
                .setProductPrice(mallProduct.getPrice().multiply(new BigDecimal(bargainRate.getBargainNumber())))
                .setUserId(detail.getUserId())
                .setSymbol(detail.getSymbol());
    }

    @Override
    @DS("read")
    public PageResult<BargainVo> ownLog(Integer page, Integer size, Integer userId) {
        IPage<BargainVo> pageResult = bargainDetailDao.ownLog(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public MallOrder submitOrder(Integer userId, Integer productId) {
        return bargainDetailDao.submitOrder(userId, productId);
    }

    @Override
    @DS("write")
    @Transactional
    public Boolean submitSuccess(Integer userId, MallProduct product, UserAddress address, BargainDetail detail, MallOrder mallOrder) {
        if (product == null || address == null || mallOrder == null) return false;
        // 超时关单
        if (!DateUtil.INSTANCE.betweenTime(detail.getAddTime(), detail.getCloseTime())) {
            // 更新状态
            MallOrder update = new MallOrder();
            update.setId(mallOrder.getId());
            update.setState(4);
            update.setCloseTime(new Date());
            mallOrderDAO.updateById(update);
            return false;
        }
        BargainRate bargainRate = bargainRateDao.selectById(product.getId());
        if (bargainRate == null) return false;
        // 库存不足
        if (product.getLeftCount() < bargainRate.getBargainNumber()) return false;
        // 更新库存
        if (mallProductDAO.updateProductHasCountById(bargainRate.getBargainNumber(), product.getId()) <= 0)
            return false;
        // 限量商品
        if (bargainRate.getIsLimit()) {
            // 限量不足
            if (bargainRate.getBargainLimit() < bargainRate.getBargainNumber()) {
                // 批量关单
                if (!closeOrders(product)) TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            } else {
                // 减限量
                BargainRate update = new BargainRate()
                        .setProductId(bargainRate.getProductId())
                        .setBargainLimit(bargainRate.getBargainLimit() - bargainRate.getBargainNumber());
                if (bargainRateDao.updateById(update) <= 0) return false;
                // 更新订单为待发货
                mallOrder.setAddressId(address.getId());
                mallOrder.setState(1);
                mallOrder.setPayTime(new Date());
                if (mallOrderDAO.updateById(mallOrder) <= 0) return false;
                // 发货后，剩余不够下次发货，提前关单
                if (bargainRate.getBargainLimit() < 2 * bargainRate.getBargainNumber()) {
                    if (!closeOrders(product)) TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return true;
            }
        }
        // 更新订单为待发货
        mallOrder.setAddressId(address.getId());
        mallOrder.setState(1);
        mallOrder.setPayTime(new Date());
        return mallOrderDAO.updateById(mallOrder) > 0;
    }

    /**
     * 批量关单
     */
    @DS("write")
    @Transactional
    public Boolean closeOrders(MallProduct product) {
        // 砍价进行中的订单关单
        List<Integer> idList = bargainDetailDao.getAllId(product.getId());
        if (idList != null && idList.size() > 0)
            if (bargainDetailDao.setState(idList) <= 0) return false;
        // 砍价成功生成的订单进行关单
        List<Integer> orderIds = bargainDetailDao.getOrderIds(product.getId());
        if (orderIds != null && orderIds.size() > 0)
            return mallOrderDAO.closeOrder(orderIds) > 0;
        return true;
    }


    @Override
    @DS("read")
    public Integer getNumber(Integer productId) {
        return bargainDetailDao.getNumber(productId);
    }

    @Override
    @DS("read")
    public PageResult<BackBargainDetailVo> bargainList(Integer page, Integer size, BargainVo bargain) {
        // 订单详情
        IPage<BackBargainDetailVo> iPage = bargainDetailDao.bargainList(new Page<>(page, size), bargain);
        List<BackBargainDetailVo> records = iPage.getRecords();
        List<Integer> idList = records.stream().map(BackBargainDetailVo::getOrderId).collect(Collectors.toList());
        if (!idList.isEmpty()) {
            // 参与人详情
            List<BargainVo> users = bargainLogDao.getAllUserInfo(idList);
            records.forEach(record -> {
                // 参与人与订单匹配
                List<BargainVo> vos = Collections.synchronizedList(new ArrayList<>());
                Iterator<BargainVo> iterator = users.iterator();
                while (iterator.hasNext()) {
                    BargainVo bargainVo = iterator.next();
                    if (bargainVo.getOrderId().equals(record.getOrderId())) {
                        vos.add(bargainVo);
                        iterator.remove();
                    }
                }
                record.setUsers(vos);
            });
        }
        return new PageResult<>(iPage.getTotal(), records);
    }

    @Override
    @DS("read")
    public BargainRate getBargainRate(Integer productId) {
        return bargainRateDao.selectById(productId);
    }

    @Override
    @DS("write")
    @Transactional
    public Boolean bargainOrderClose(Integer id) {
        BargainDetail update = new BargainDetail()
                .setId(id)
                .setState(true);
        if (bargainDetailDao.updateById(update) < 0) {
            return false;
        }
        // 如果已砍成但错过提交订单时间，则需关闭对应的商城订单
        BargainDetail bargainDetail = bargainDetailDao.selectById(id);
        if (bargainDetail.getOrderNumber() != null) {
            LambdaQueryWrapper<MallOrder> queryWrapper = new QueryWrapper<MallOrder>()
                    .lambda().eq(MallOrder::getOrderNumber, bargainDetail.getOrderNumber());
            MallOrder mallOrder = mallOrderDAO.selectOne(queryWrapper);
            if (mallOrder != null) {
                mallOrder.setState(MallEnum.ORDER_HAS_CLOSE.getState());
                return mallOrderDAO.updateById(mallOrder) > 0;
            }
        }
        return true;
    }

    @Override
    @DS("read")
    public List<BargainDetail> needCloseList() {
        LambdaQueryWrapper<BargainDetail> queryWrapper = new QueryWrapper<BargainDetail>()
                .lambda().eq(BargainDetail::getState, false);
        return bargainDetailDao.selectList(queryWrapper);
    }
}
