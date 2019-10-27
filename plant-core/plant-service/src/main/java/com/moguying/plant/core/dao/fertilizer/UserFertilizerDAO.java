package com.moguying.plant.core.dao.fertilizer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.fertilizer.vo.FertilizerUseCondition;
import com.moguying.plant.core.entity.user.vo.UserFertilizerInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserFertilizerDAO继承基类
 */
@Repository
public interface UserFertilizerDAO extends BaseMapper<UserFertilizer> {
    List<UserFertilizer> selectSelective(UserFertilizer userFertilizer);

    Integer fertilizerIsUsing(Integer fertilizerId);

    IPage<UserFertilizerInfo> userFertilizers(Page<UserFertilizerInfo> page, @Param("wq") FertilizerUseCondition condition);

    List<UserFertilizerInfo> selectByIds(@Param("idList") List<Integer> idList);

    Integer updateStateByIds(@Param("idList") List<Integer> idList, @Param("userFertilizer") UserFertilizer userFertilizer);

    Integer updateOutTimeFertilizer(Integer userId);

    Integer updateStateByOrderNumber(@Param("orderNumber") String orderNumber, @Param("state") Integer state);

    Integer hasFertilizer(@Param("userId") Integer userId, @Param("fertilizerId") Integer fertilizerId);

    Integer countByTriggerEvent(@Param("userId") Integer userId, @Param("event") String triggerEvent);

    UserFertilizer findByIdAndUserId(@Param("userId") Integer userId, @Param("fertilizerId") Integer fertilizerId);

    UserFertilizer getUserFertilizer(@Param("userId") Integer userId, @Param("id") Integer id, @Param("type") Integer type);
}