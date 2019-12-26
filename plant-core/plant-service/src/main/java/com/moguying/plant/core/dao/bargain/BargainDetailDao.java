package com.moguying.plant.core.dao.bargain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.vo.BackBargainDetailVo;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.mall.MallOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BargainDetailDao extends BaseDAO<BargainDetail> {

    List<BargainDetail> getOneByOpen(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("state") Boolean state);

    BargainDetail getOneByClose(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("state") Boolean state);

    IPage<BargainVo> successLogs(Page<BargainVo> page);

    List<SendNumberVo> sendNumber();

    IPage<BargainVo> doingList(Page<BargainVo> page, @Param("userId") Integer userId);

    IPage<BargainVo> ownLog(Page<BargainVo> page, @Param("userId") Integer userId);

    MallOrder submitOrder(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Integer> getAllId(@Param("productId") Integer productId);

    List<Integer> getOrderIds(@Param("productId") Integer productId);

    Integer setState(@Param("idList") List<Integer> idList);

    Integer getNumber(@Param("productId") Integer productId);

    IPage<BackBargainDetailVo> bargainList(Page<BackBargainDetailVo> page, @Param("bargain") BargainVo bargainVo);

}
