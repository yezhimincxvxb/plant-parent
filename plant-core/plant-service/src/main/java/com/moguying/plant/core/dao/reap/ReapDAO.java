package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.constant.ReapEnum;
import com.moguying.plant.core.entity.dto.InnerMessage;
import com.moguying.plant.core.entity.dto.Reap;
import com.moguying.plant.core.entity.vo.ExchangeInfo;
import com.moguying.plant.core.entity.vo.TotalMoney;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ReapDAO继承基类
 */
@Repository
public interface ReapDAO extends BaseMapper<Reap> {

    List<Reap> selectSelective(Reap reap);
    Integer updateStateByRange(@Param("idList") List<Integer> idList, @Param("reap") Reap update);
    List<Integer> selectCanReapByTime(@Param("reapTime") Date reapTime);
    List<InnerMessage> selectPhoneByRange(@Param("idList") List<Integer> idList);
    List<Reap> selectReapInRange(@Param("idList") List<Integer> idList, @Param("reap") Reap where);
    Integer countBlockIdByUserId(Integer userId);
    Integer countByUserIdAndGrowUpDays(@Param("userId") Integer userId, @Param("days") Integer days);
    List<Reap> selectListCountByUserId(@Param("userId") Integer userId, @Param("state") Integer state);
    BigDecimal reapProfitStatistics(@Param("userId") Integer userId, @Param("startTime") Date startTime,
                                    @Param("endTime") Date endTime, @Param("states") List<ReapEnum> states);
    BigDecimal plantProfitStatistics(@Param("userId") Integer userId, @Param("startTime") Date startTime,
                                     @Param("endTime") Date endTime, @Param("state") Integer state);
    Integer reapStatistics(@Param("userId") Integer userId, @Param("state") Integer state, @Param("isEqual") boolean isEqual);
    Integer countPlantedByDateAndUserId(@Param("userId") Integer userId, @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);
    Reap selectNewestByUserId(@Param("userId") Integer userId);
    Reap selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 查询已采摘
     */
    List<ExchangeInfo> showReap(Integer userId);

    /**
     * 根据ID集合查询
     */
    List<Reap> findReapListByName(@Param("productName") String productName, @Param("userId") Integer userId);

    /**
     * 计算蘑菇币
     */
    Integer sumCoin(@Param("userId") Integer userId, @Param("idList") List<Integer> idList);

    /**
     * 查询资金
     */
    TotalMoney findMoney(@Param("idList") List<Integer> idList);

    /**
     * 批量更新状态
     */
    int updateState(@Param("idList") List<Integer> idList);

    /**
     * 查询兑换蘑菇币记录
     */
    List<ExchangeInfo> showReapLog(Integer userId);

}