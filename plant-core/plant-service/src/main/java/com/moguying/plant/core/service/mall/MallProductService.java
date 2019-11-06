package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.common.vo.HomeProduct;
import com.moguying.plant.core.entity.common.vo.HomeProductDetail;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.OrderBuy;
import com.moguying.plant.core.entity.mall.vo.OrderBuyResponse;
import com.moguying.plant.core.entity.mall.vo.OrderSum;
import com.moguying.plant.core.entity.seed.vo.SubmitOrder;

public interface MallProductService {

    ResultData<OrderBuyResponse> orderBuy(Integer userId, OrderBuy orderBuy);

    ResultData<Integer> saveProduct(MallProduct addProduct);

    boolean showProduct(Integer id);

    PageResult<MallProduct> productList(Integer page, Integer size, MallProduct where);

    ResultData<BuyResponse> submitOrder(SubmitOrder submitOrder, Integer userId);

    ResultData<MallProduct> productDetail(Integer id);

    ResultData<OrderSum> sumOrder(Integer userId, SubmitOrder submitOrder);

    PageResult<HomeProduct> productListForHome(Integer page, Integer size, HomeProduct search);

    HomeProductDetail productDetailForAppMall(Integer id);

    PageResult<ExchangeInfo> showProducts(Integer page, Integer size);

    PageResult<ExchangeInfo> showProductLog(Integer page, Integer size, Integer userId);

    PageResult<BargainVo> productList(Integer page, Integer size);

    BargainVo productInfo(Integer productId);

    Integer updateProductToBargain(MallProduct mallProduct);
}
