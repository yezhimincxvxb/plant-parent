package com.moguying.plant.core.service.reap;

import com.moguying.plant.constant.ReapEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.system.InnerMessage;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.user.vo.TotalMoney;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReapService {

    @DataSource("read")
    PageResult<Reap> reapList(Integer page, Integer size, Reap where);

    @DataSource("write")
    Integer updateReapState(List<Integer> idList, Reap update);

    @DataSource("write")
    Integer reapDone(List<Integer> idList);

    @DataSource("read")
    List<Reap> reapListByUserId(Integer userId);

    @DataSource("read")
    BigDecimal reapProfitStatistics(Integer userId, Date startTime, Date endTime, List<ReapEnum> reapEnums);

    @DataSource("read")
    BigDecimal plantProfitStatistics(Integer userId, Date startTime, Date endTime, ReapEnum reapEnum);

    @DataSource("read")
    Integer reapStatistics(Integer userId, ReapEnum reapEnum, Boolean isEqual);

    @DataSource("write")
    ResultData<TriggerEventResult<InnerMessage>> saleReap(Integer seedType, Integer userId);

    @DataSource("read")
    Reap reapInfoByIdAndUserId(Integer id, Integer userId);

    @DataSource("write")
    Integer autoReap(Date reapTime);

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<Reap> search, HttpServletRequest request);

    /**
     * 查询已采摘
     */
    @DataSource("read")
    PageResult<ExchangeInfo> showReap(Integer page, Integer size, Integer userId);

    /**
     * 根据ID集合查询
     */
    @DataSource("read")
    List<Reap> findReapListByName(String productName, Integer userId);

    /**
     * 计算蘑菇币
     */
    @DataSource("read")
    Integer sumCoin(Integer userId, List<Integer> idList);

    /**
     * 查询资金
     */
    @DataSource("read")
    TotalMoney findMoney(List<Integer> idList);

    /**
     * 兑换成功，对数据进行操作
     */
    @DataSource("write")
    Boolean exchangeMogubi(Integer userId, UserMoney userMoney, List<Reap> reaps);

    /**
     * 查询兑换蘑菇币记录
     */
    @DataSource("read")
    PageResult<ExchangeInfo> showReapLog(Integer page, Integer size, Integer userId);

    /**
     * 兑换券
     */
    @DataSource("write")
    Boolean exchangeFertilizer(Integer userId, Integer fertilizerId, SaleCoin saleCoin, Fertilizer fertilizer);

}
