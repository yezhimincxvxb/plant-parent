package com.moguying.plant.core.service.reap;

import com.moguying.plant.constant.ReapEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.index.CapitalChange;
import com.moguying.plant.core.entity.index.SeedDetailInfo;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.vo.TotalMoney;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReapService {

    PageResult<Reap> reapList(Integer page, Integer size, Reap where);

    PageResult<Reap> userReapList(Integer page, Integer size, Reap where);

    Integer updateReapState(List<Integer> idList, Reap update);

    Integer reapDone(List<Integer> idList);

    List<Reap> reapListByUserId(Integer userId);

    BigDecimal reapProfitStatistics(Integer userId, Date startTime, Date endTime, List<ReapEnum> reapEnums);

    BigDecimal plantProfitStatistics(Integer userId, Date startTime, Date endTime, ReapEnum reapEnum);

    Integer reapStatistics(Integer userId, ReapEnum reapEnum, Boolean isEqual);

    ResultData<TriggerEventResult<InnerMessage>> saleReap(Integer reapId, Integer userId);

    Reap reapInfoByIdAndUserId(Integer id, Integer userId);

    Integer autoReap(Date reapTime);

    void downloadExcel(Integer userId, PageSearch<Reap> search, HttpServletRequest request);

    /**
     * 查询已采摘
     */
    PageResult<ExchangeInfo> showReap(Integer page, Integer size, Integer userId);

    /**
     * 根据ID集合查询
     */
    List<Reap> findReapListByName(String productName, Integer userId);

    /**
     * 计算蘑菇币
     */
    Integer sumCoin(Integer userId, List<Integer> idList);

    /**
     * 查询资金
     */
    TotalMoney findMoney(List<Integer> idList);

    /**
     * 兑换成功，对数据进行操作
     */
    Boolean exchangeMogubi(Integer userId, UserMoney userMoney, List<Reap> reaps);

    /**
     * 查询兑换蘑菇币记录
     */
    PageResult<ExchangeInfo> showReapLog(Integer page, Integer size, Integer userId);

    /**
     * 兑换券
     */
    Boolean exchangeFertilizer(Integer userId, Integer fertilizerId, SaleCoin saleCoin, Fertilizer fertilizer);


    /**
     * 获取种植总利润
     */
    BigDecimal getPlantProfits();


    /**
     * 获取种植总额
     */
    BigDecimal getPlantLines();

    Integer getPlantNum(Integer state);

    List<SeedDetailInfo> getSeedDetailInfo(List<Integer> types, Integer state, Integer i);

    CapitalChange capitalChange(Integer state);
}
