package com.moguying.plant.core.service.mall.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.mall.MallOrderDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.common.vo.HomeProduct;
import com.moguying.plant.core.entity.common.vo.HomeProductDetail;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.*;
import com.moguying.plant.core.entity.seed.vo.SubmitOrder;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.scheduled.CloseOrderScheduled;
import com.moguying.plant.core.scheduled.task.CloseMallPayOrder;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.reap.SaleCoinService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class MallProductServiceImpl implements MallProductService {

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private MallOrderDAO mallOrderDAO;

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Autowired
    private CloseOrderScheduled closeOrderScheduled;

    @Autowired
    private SaleCoinService saleCoinService;

    @Value("${order.expire.time}")
    private Long expireTime;

    @Value("${meta.content.img}")
    private String appendStr;

    @Override
    @DS("read")
    public ResultData<OrderBuyResponse> orderBuy(Integer userId, OrderBuy orderBuy) {
        ResultData<OrderBuyResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        OrderBuyResponse buyOrderResponse = new OrderBuyResponse();

        // 默认收货地址
        UserAddress address = userAddressDAO.userDefaultAddress(userId);
        if (null != address)  buyOrderResponse.setAddress(address);

        List<BuyProduct> buyProducts = orderBuy.getProducts();
        if (buyProducts == null || buyProducts.size() <= 0)
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        // 订单商品摘要信息
        List<OrderItem> orderItems = Collections.synchronizedList(new ArrayList<>());
        BigDecimal expressFee = BigDecimal.ZERO;
        BigDecimal productAmount = BigDecimal.ZERO;
        int totalCoins = 0;
        for (BuyProduct buyProduct : buyProducts) {
            if (buyProduct == null) continue;

            // 库存不足
            MallProduct mallProduct = mallProductDAO.selectById(buyProduct.getProductId());
            if (null == mallProduct || buyProduct.getBuyCount() > mallProduct.getLeftCount())
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);

            OrderItem orderItem = new OrderItem();
            orderItem.setBuyCount(buyProduct.getBuyCount());
            orderItem.setExpressFee(mallProduct.getFee());
            orderItem.setProductId(mallProduct.getId());
            orderItem.setProductName(mallProduct.getName());
            orderItem.setProductPrice(mallProduct.getPrice());
            orderItem.setCoin(mallProduct.getConsumeCoins());
            orderItem.setProductSummary(mallProduct.getSummaryDesc());
            orderItem.setThumbPicUrl(mallProduct.getThumbPicUrl());
            orderItems.add(orderItem);

            // 运费最大值
            expressFee = expressFee.compareTo(mallProduct.getFee()) > 0 ? expressFee : mallProduct.getFee();
            productAmount = productAmount.add(mallProduct.getPrice().multiply(new BigDecimal(buyProduct.getBuyCount())));
            totalCoins += mallProduct.getConsumeCoins() * buyProduct.getBuyCount();
        }

        buyOrderResponse.setOrderItems(orderItems);
        buyOrderResponse.setExpressFee(expressFee);
        buyOrderResponse.setProductAmount(productAmount);
        buyOrderResponse.setTotalAmount(productAmount.add(expressFee));
        buyOrderResponse.setTotalCoins(totalCoins);
        buyOrderResponse.setItemCount(orderItems.size());
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(buyOrderResponse);
    }


    /**
     * 保存商品
     *
     * @param product
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> saveProduct(MallProduct product) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        if(null != product.getProductDesc())
            product.setProductDesc(appendStr.concat(product.getProductDesc()));
        if (null != product.getId()) {
            if (null == mallProductDAO.selectById(product.getId())) {
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);
            }
            resultData.setData(mallProductDAO.updateById(product));
        } else {
            // 添加时，剩余库存为总库存
            product.setLeftCount(product.getTotalCount());
            product.setAddTime(new Date());
            resultData.setData(mallProductDAO.insert(product));
        }
        if (resultData.getData() > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(product.getId());

        return resultData;
    }

    @Override
    @DS("write")
    public boolean showProduct(Integer id) {
        MallProduct product = mallProductDAO.selectById(id);
        if (null == product)
            return false;
        MallProduct saveProduct = new MallProduct();
        saveProduct.setId(id);
        saveProduct.setIsShow(!product.getIsShow());
        return saveProduct(saveProduct).getMessageEnum().equals(MessageEnum.SUCCESS);
    }

    @Override
    @DS("read")
    public PageResult<MallProduct> productList(Integer page, Integer size, MallProduct where) {
        IPage<MallProduct> pageResult = mallProductDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }


    /**
     * 提交订单
     *
     * @param submitOrder
     * @param userId
     * @return
     */
    @Override
    @Transactional
    @DS("write")
    public ResultData<BuyResponse> submitOrder(SubmitOrder submitOrder, Integer userId) {
        ResultData<BuyResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 商品信息
        List<BuyProduct> buyProducts = submitOrder.getProducts();
        if (buyProducts == null || buyProducts.size() <= 0)
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        // 地址
        if (null == submitOrder.getAddressId()) return resultData.setMessageEnum(MessageEnum.MALL_ORDER_ADDRESS_EMPTY);
        UserAddress address = userAddressDAO.selectByIdAndUserId(submitOrder.getAddressId(), userId, false);
        if (null == address) return resultData.setMessageEnum(MessageEnum.USER_ADDRESS_NO_EXISTS);
        BuyResponse response = new BuyResponse();
        response.setAddress(address);

        // 生成订单
        String orderNumber = OrderPrefixEnum.MALL_PRODUCT_ORDER.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate();
        MallOrder order = new MallOrder();
        order.setUserId(userId);
        order.setOrderNumber(orderNumber);
        order.setAddressId(address.getId());
        order.setBuyMark(submitOrder.getMark());
        order.setAddTime(new Date());
        if (mallOrderDAO.insert(order) <= 0)
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_CREATE_ERROR);

        order = mallOrderDAO.findByIdAndNum(userId, orderNumber);
        if (order == null) return resultData;

        BigDecimal feeAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        Integer totalCoins = 0;
        for (BuyProduct buyProduct : buyProducts) {
            if (buyProduct == null) continue;

            // 是否上架
            MallProduct product = mallProductDAO.selectById(buyProduct.getProductId());
            if (!product.getIsShow()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);
            }

            // 库存是否足够
            if (product.getLeftCount() < buyProduct.getBuyCount()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_ENOUGH);
            }

            // 更新库存
            if (mallProductDAO.updateProductHasCountById(buyProduct.getBuyCount(), buyProduct.getProductId()) <= 0)
                return resultData.setMessageEnum(MessageEnum.MALL_ORDER_UPDATE_ERROR);

            // 生成订单详情
            MallOrderDetail detail = new MallOrderDetail();
            detail.setOrderId(order.getId());
            detail.setUserId(userId);
            detail.setProductId(product.getId());
            detail.setBuyCount(buyProduct.getBuyCount());
            detail.setBuyAmount(product.getPrice().multiply(new BigDecimal(buyProduct.getBuyCount())));
            if (submitOrder.getState() != null && submitOrder.getState() == 2) {
                // 蘑菇币兑换
                detail.setBuyCoins(product.getConsumeCoins() * buyProduct.getBuyCount());
            } else {
                // 现金支付
                detail.setBuyCoins(0);
            }
            totalCoins += detail.getBuyCoins();
            totalAmount = totalAmount.add(detail.getBuyAmount());
            if (mallOrderDetailDAO.insert(detail) <= 0)
                return resultData.setMessageEnum(MessageEnum.MALL_ORDER_DETAIL_CREATE_ERROR);

            // 运费最大值
            feeAmount = feeAmount.compareTo(product.getFee()) >= 0 ? feeAmount : product.getFee();
        }

        order.setFeeAmount(feeAmount);
        order.setBuyAmount(totalAmount);
        if (submitOrder.getState() != null && submitOrder.getState() == 2) {
            // 蘑菇币是否足够
            SaleCoin saleCoin = saleCoinService.findById(userId);
            if (saleCoin == null || saleCoin.getCoinCount() < totalCoins) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData.setMessageEnum(MessageEnum.COINS_NOT_ENOUGH);
            }

            // 更新运费和蘑菇币
            order.setTotalCoins(totalCoins);

            if (mallOrderDAO.updateById(order) <= 0)
                return resultData.setMessageEnum(MessageEnum.MALL_ORDER_UPDATE_ERROR);

        } else {
            // 更新运费和购买总价
            if (mallOrderDAO.updateById(order) <= 0)
                return resultData.setMessageEnum(MessageEnum.MALL_ORDER_UPDATE_ERROR);
        }

        // 添加至关单队列
        closeOrderScheduled.addCloseItem(new CloseMallPayOrder(order.getId(), expireTime));
        response.setOrderId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(totalAmount.add(feeAmount));
        response.setTotalCoins(totalCoins);
        response.setLeftSecond(expireTime.intValue());
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(response);

    }

    @Override
    @DS("read")
    public ResultData<MallProduct> productDetail(Integer id) {
        ResultData<MallProduct> resultData = new ResultData<>(MessageEnum.ERROR, null);
        MallProduct product = mallProductDAO.selectById(id);
        if (null == product)
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(product);
    }

    @Override
    @DS("read")
    public ResultData<OrderSum> sumOrder(SubmitOrder submitOrder) {
        ResultData<OrderSum> resultData = new ResultData<>(MessageEnum.ERROR, null);

        List<BuyProduct> buyProducts = submitOrder.getProducts();
        if (buyProducts == null || buyProducts.size() <= 0)
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        AtomicInteger buyCount = new AtomicInteger();
        BigDecimal productAmount = new BigDecimal("0");
        BigDecimal feeAmount = new BigDecimal("0");
        int totalCoins = 0;
        for (BuyProduct buyProduct : buyProducts) {
            if (buyProduct == null) continue;

            // 库存不足
            MallProduct product = mallProductDAO.selectById(buyProduct.getProductId());
            if (null == product || buyProduct.getBuyCount() > product.getLeftCount())
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);

            feeAmount = feeAmount.compareTo(product.getFee()) > 0 ? feeAmount : product.getFee();
            productAmount = productAmount.add(product.getPrice().multiply(new BigDecimal(buyProduct.getBuyCount())));
            totalCoins += product.getConsumeCoins() * buyProduct.getBuyCount();
            buyCount.getAndAdd(buyProduct.getBuyCount());
        }

        OrderSum orderSum = new OrderSum();
        orderSum.setProductAmount(productAmount);
        orderSum.setExpressFee(feeAmount);
        orderSum.setTotalAmount(productAmount.add(feeAmount));
        orderSum.setTotalCoins(totalCoins);
        orderSum.setBuyCount(buyCount.get());
        return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(orderSum);
    }

    @Override
    @DS("read")
    public PageResult<HomeProduct> productListForHome(Integer page, Integer size, HomeProduct search) {
        mallProductDAO.selectProductForApp(new Page<>(page,size),search);
        return null;
    }

    @Override
    @DS("read")
    public HomeProductDetail productDetailForAppMall(Integer id) {
        return mallProductDAO.selectProductDetailForApp(id);
    }

    @Override
    @DS("read")
    public PageResult<ExchangeInfo> showProducts(Integer page, Integer size) {
        IPage<ExchangeInfo> pageResult = mallProductDAO.showProducts(new Page<>(page, size));
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("read")
    public PageResult<ExchangeInfo> showProductLog(Integer page, Integer size, Integer userId) {
        IPage<ExchangeInfo> pageResult = mallProductDAO.showProductLog(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }
}
