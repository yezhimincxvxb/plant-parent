package com.moguying.plant.core.service.bargain.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.bargain.BargainDetailDao;
import com.moguying.plant.core.dao.bargain.BargainLogDao;
import com.moguying.plant.core.dao.mall.MallOrderDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    /**
     * 用户本人砍价比率 [50,60]
     */
    @Value("${bargain.own.rate}")
    private Integer userRate;

    /**
     * 新用户砍价比率 [20,30]
     */
    @Value("${bargain.new.rate}")
    private Integer newUserRate;

    /**
     * 老用户砍价比率 [10,20]
     */
    @Value("${bargain.old.rate}")
    private Integer oldUserRate;

    /**
     * 获取比率
     */
    private BigDecimal getRate(Integer val) {
        return new BigDecimal(val + new Random().nextInt(11)).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_UP);
    }

    @Override
    @DS("read")
    public BargainDetail getOneByClose(Integer userId, Integer productId, Boolean state) {
        return bargainDetailDao.getOneByClose(userId, productId, state);
    }

    @Override
    public BargainDetail getOneById(Integer id) {
        return bargainDetailDao.selectById(id);
    }

    @Override
    @DS("write")
    @Transactional
    public BargainDetail shareSuccess(Integer userId, BuyProduct buyProduct, MallProduct product) {

        if (buyProduct == null || product == null) return null;

        // 重复分享
        List<BargainDetail> details = bargainDetailDao.getOneByOpen(userId, buyProduct.getProductId(), false);
        if (details != null && !details.isEmpty()) {
            // 删除多余的订单
            if (details.size() >= 2) {
                for (int i = 1; i < details.size(); i++) {
                    bargainDetailDao.deleteById(details.get(i));
                }
            }
            // 只获取第一单
            BargainDetail detail = details.get(0);
            detail.setMessage("分享成功");
            return detail;
        }

        // 总价、第一刀砍了多少
        BigDecimal totalAmount = product.getPrice().multiply(new BigDecimal(product.getBargainNumber()));
        BigDecimal bargainAmount = totalAmount.multiply(getRate(userRate));

        // 首次分享，生成砍价详情
        BargainDetail add = new BargainDetail();
        add.setUserId(userId);
        add.setProductId(product.getId());
        add.setProductCount(product.getBargainNumber());
        add.setTotalAmount(totalAmount);
        add.setBargainAmount(bargainAmount);
        add.setLeftAmount(totalAmount.subtract(bargainAmount));
        add.setTotalCount(product.getBargainCount());
        add.setBargainCount(1);
        add.setAddTime(new Date());
        add.setBargainTime(new Date());
        add.setCloseTime(DateUtil.INSTANCE.nextDay(new Date()));
        if (bargainDetailDao.insert(add) <= 0) return null;

        // 日志
        BargainLog log = new BargainLog();
        log.setUserId(userId);
        log.setShareId(userId);
        log.setProductId(product.getId());
        log.setDetailId(add.getId());
        log.setHelpAmount(bargainAmount);
        log.setHelpTime(new Date());
        if (bargainLogDao.insert(log) > 0) {
            add.setMessage("首次分享");
            return add;
        }
        return null;
    }

    @Override
    @DS("write")
    @Transactional
    public BargainLog helpSuccess(Integer userId, BargainDetail detail) {

        BargainDetail update;
        // 关单
        if (!DateUtil.INSTANCE.betweenTime(detail.getAddTime(), detail.getCloseTime()) || detail.getTotalCount() <= detail.getBargainCount()) {
            // 更新状态
            update = new BargainDetail();
            update.setId(detail.getId());
            update.setState(true);
            bargainDetailDao.updateById(update);
            return null;
        }

        // 更新
        update = new BargainDetail();

        // 帮砍价格
        BigDecimal helpAmount = BigDecimal.ZERO;
        // 最后一刀
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
            update.setState(true);
            update.setOrderNumber(orderNumber);

            // 全部砍完
            helpAmount = detail.getLeftAmount();
        } else {
            User user = userDAO.selectById(userId);
            if (user == null) return null;

            //  注册时间在砍价详情生成之后，默认为新用户
            if (user.getRegTime().after(detail.getAddTime())) {
                helpAmount = detail.getLeftAmount().multiply(getRate(newUserRate));
            } else {
                helpAmount = detail.getLeftAmount().multiply(getRate(oldUserRate));
            }
        }

        update.setId(detail.getId());
        update.setBargainAmount(detail.getBargainAmount().add(helpAmount));
        update.setLeftAmount(detail.getLeftAmount().subtract(helpAmount));
        update.setBargainCount(detail.getBargainCount() + 1);
        update.setBargainTime(new Date());
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
    public PageResult<BargainVo> doingList(Integer page, Integer size, Integer userId) {
        IPage<BargainVo> pageResult = bargainDetailDao.doingList(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public PageResult<BargainVo> successList(Integer page, Integer size, Integer userId) {
        IPage<BargainVo> pageResult = bargainDetailDao.successList(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public BargainVo productInfoByOrderId(Integer orderId) {
        BargainDetail detail = bargainDetailDao.selectById(orderId);
        if (detail == null) return null;

        MallProduct mallProduct = mallProductDAO.selectById(detail.getProductId());
        if (mallProduct == null) return null;

        BargainVo vo = new BargainVo();
        vo.setOrderId(detail.getId());
        vo.setProductName(mallProduct.getName());
        vo.setBargainAmount(detail.getBargainAmount());
        vo.setLeftAmount(detail.getLeftAmount());
        vo.setProductInfo(mallProduct.getSummaryDesc());
        vo.setPicUrl(mallProduct.getPicUrl());
        vo.setBeginTime(DateUtil.INSTANCE.formatDateForPayment(detail.getAddTime()));
        vo.setEndTime(DateUtil.INSTANCE.formatDateForPayment(detail.getCloseTime()));
        vo.setRate(detail.getBargainAmount().divide(detail.getTotalAmount(), 2));
        vo.setProductId(mallProduct.getId());
        vo.setProductPrice(mallProduct.getPrice().multiply(new BigDecimal(mallProduct.getBargainNumber())));
        vo.setUserId(detail.getUserId());
        return vo;
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

        // 库存不足
        if (product.getLeftCount() < product.getBargainNumber()) return false;

        // 更新库存
        if (mallProductDAO.updateProductHasCountById(product.getBargainNumber(), product.getId()) <= 0) return false;

        // 限量商品
        if (product.getIsLimit()) {
            // 限量不足
            if (product.getBargainLimit() < product.getBargainNumber()) {
                // 批量关单
                if (!closeOrders(product)) TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            } else {
                // 减限量
                MallProduct mallProduct = new MallProduct();
                mallProduct.setId(product.getId());
                mallProduct.setBargainLimit(product.getBargainLimit() - product.getBargainNumber());
                if (mallProductDAO.updateById(mallProduct) <= 0) return false;

                // 更新订单为待发货
                mallOrder.setAddressId(address.getId());
                mallOrder.setState(1);
                mallOrder.setPayTime(new Date());
                if (mallOrderDAO.updateById(mallOrder) <= 0) return false;

                // 发货后，剩余不够下次发货，提前关单
                if (product.getBargainLimit() < 2 * product.getBargainNumber()) {
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
    public Integer getNumber(Integer productId) {
        return bargainDetailDao.getNumber(productId);
    }
}
