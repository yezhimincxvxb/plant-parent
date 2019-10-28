package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.mall.MallCar;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * MallCarDAO继承基类
 */
@Repository
public interface MallCarDAO extends BaseDAO<MallCar> {
    IPage<OrderItem> userCarItemList(Page<OrderItem> page, @Param("userId") Integer userId);
    Integer addCarItemCount(@Param("id") Integer id, @Param("count") Integer count);
    MallCar selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    Integer deleteItemByRange(@Param("items") List<OrderItem> items, @Param("userId") Integer userId);
    BigDecimal sumCheckedItemAmount(Integer userId);
    Integer checkItems(@Param("items") List<OrderItem> items, @Param("userId") Integer userId, @Param("check") Boolean check);
    Integer countByCheckState(@Param("check") Boolean check, @Param("userId") Integer userId);
}