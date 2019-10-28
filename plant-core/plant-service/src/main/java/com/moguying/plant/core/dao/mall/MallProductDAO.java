package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.bargain.vo.BargainResponse;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.common.vo.HomeProduct;
import com.moguying.plant.core.entity.common.vo.HomeProductDetail;
import com.moguying.plant.core.entity.mall.MallProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * MallProductDAO继承基类
 */
@Repository
public interface MallProductDAO extends BaseMapper<MallProduct> {
    IPage<MallProduct> selectSelective(Page<MallProduct> page, @Param("wq") MallProduct where);

    Integer updateProductHasCountById(@Param("count") Integer count, @Param("productId") Integer productId);

    IPage<HomeProduct> selectProductForApp(Page<MallProduct> page, @Param("wq") HomeProduct search);

    HomeProductDetail selectProductDetailForApp(Integer id);

    Integer productCountEnough(@Param("productId") Integer productId, @Param("count") Integer count);

    IPage<ExchangeInfo> showProducts(Page<ExchangeInfo> page);

    IPage<ExchangeInfo> showProductLog(Page<ExchangeInfo> page, @Param("userId") Integer userId);

    IPage<BargainResponse> productList(Page<BargainResponse> page);

    BargainResponse productInfo(@Param("id") Integer id);
}