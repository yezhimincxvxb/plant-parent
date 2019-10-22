package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.vo.MallCar;
import com.moguying.plant.core.entity.vo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * MallCarDAO继承基类
 */
@Repository
public interface MallCarDAO extends BaseMapper<MallCar> {
    List<OrderItem> userCarItemList(Integer userId);
    Integer addCarItemCount(@Param("id") Integer id, @Param("count") Integer count);
    MallCar selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    Integer deleteItemByRange(@Param("items") List<OrderItem> items, @Param("userId") Integer userId);
    BigDecimal sumCheckedItemAmount(Integer userId);
    Integer checkItems(@Param("items") List<OrderItem> items, @Param("userId") Integer userId, @Param("check") Boolean check);
    Integer countByCheckState(@Param("check") Boolean check, @Param("userId") Integer userId);
}