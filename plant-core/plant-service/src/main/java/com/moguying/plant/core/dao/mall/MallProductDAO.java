package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.common.vo.HomeProduct;
import com.moguying.plant.core.entity.common.vo.HomeProductDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallProductDAO继承基类
 */
@Repository
public interface MallProductDAO extends BaseMapper<MallProduct> {
    List<MallProduct> selectSelective(MallProduct where);
    Integer updateProductHasCountById(@Param("count") Integer count, @Param("productId") Integer productId);
    List<HomeProduct> selectProductForApp(HomeProduct search);
    HomeProductDetail selectProductDetailForApp(Integer id);
    Integer productCountEnough(@Param("productId") Integer productId, @Param("count") Integer count);

    /**
     * 兑换实物列表
     */
    List<ExchangeInfo> showProducts();

    /**
     * 兑换实物日志列表
     */
    List<ExchangeInfo> showProductLog(Integer userId);
}