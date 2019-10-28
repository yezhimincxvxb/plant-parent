package com.moguying.plant.core.dao.bargain;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.vo.BargainResponse;
import com.moguying.plant.core.entity.bargain.vo.SendNumber;
import com.moguying.plant.core.entity.mall.MallOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BargainDetailDao extends BaseMapper<BargainDetail> {

    BargainDetail getOneByOpen(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("state") Boolean state);

    BargainDetail getOneByClose(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("state") Boolean state);

    IPage<BargainResponse> successLogs(Page<BargainResponse> page);

    List<SendNumber> sendNumber();

    IPage<BargainResponse> doingList(Page<BargainResponse> page, @Param("userId") Integer userId, @Param("id") Integer id);

    IPage<BargainResponse> ownLog(Page<BargainResponse> page, @Param("userId") Integer userId);

    MallOrder submitOrder(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Integer> getAllId(@Param("productId") Integer productId);

    List<Integer> getOrderIds(@Param("productId") Integer productId);

    Integer setState(@Param("idList") List<Integer> idList);

    Integer getNumber(@Param("productId") Integer productId);

}
