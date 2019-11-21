package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首页统计总表
 */
@Data
public class BHomeExcelTotal implements Serializable {

    private static final long serialVersionUID = 8198155859158031646L;
    /**
     * 注册人数
     */
    @JSONField(ordinal = 1)
    private Integer enrollment;
    /**
     * 实名人数
     */
    @JSONField(ordinal = 1)
    private Integer realNameNum;

    /**
     * 购买菌包人数
     */
    @JSONField(ordinal = 3)
    private Integer peopleBuy;
    /**
     * 购买菌包份数
     */
    @JSONField(ordinal = 4)
    private Integer buyCopies;
    /**
     * 种植人数
     */
    @JSONField(ordinal = 5)
    private Integer plantNum;
    /**
     * 总支付分红
     */
    @JSONField(ordinal = 6)
    private BigDecimal payDividends;
    /**
     * 总可用余额
     */
    @JSONField(ordinal = 7)
    private BigDecimal totalAvailableBalance;
    /**
     * 审核通过提现总额 withdraw
     */
    @JSONField(ordinal = 8)
    private BigDecimal withdraw;
    /**
     * 待审核提现总额
     */
    @JSONField(ordinal = 9)
    private BigDecimal waitWithdraw;
    /**
     * 电商平台订单数量
     */
    @JSONField(ordinal = 10)
    private BigDecimal mallOrderNum;
    /**
     * 电商平台下单人数
     */
    @JSONField(ordinal = 11)
    private BigDecimal mallUserNum;
    /**
     * 电商平台下单金额
     */
    @JSONField(ordinal = 12)
    private BigDecimal totalMallAmount;

//    //统计注册用户和实名人数enrollment
//    homeTotal.setRegNum(userDAO.regUserTotal(1,null,search.getStartTime(),search.getEndTime()));
//    realNameNum
//    homeTotal.setRegNum(userDAO.regUserTotal(null,1,search.getStartTime(),search.getEndTime()));
//    //注册转化率CR
//    //统计总购买菌包人数PeopleBuy
//    homeTotal.setBuyUserNum(userDAO.buySeedUserTotal(search.getStartTime(),search.getEndTime()));
//    //统计总购买菌包份数BuyCopies
//    homeTotal.setBuySeedCount(userDAO.buySeedCount(search.getStartTime(),search.getEndTime()));
//    //统计总购买菌包总金额（用于计算购买转化率）Total purchase amount/Purchase conversion
//    userDAO.buyAmount(search.getStartTime(),search.getEndTime());
//    //统计总种植人数
//    homeTotal.setPlantNum(userDAO.plantNum(search.getStartTime(),search.getEndTime()));
//    //种植转化率 = 种植数/购买菌包数/Planting conversion
//    //总支付分红=pre_profit 预计收益Pay dividends
//    homeTotal.setPayShare(userDAO.payShare(search.getStartTime(),search.getEndTime()));
//    //总可用余额 Total available balance
//    //审核通过提现总额 withdraw
//    homeTotal.setSuccessMoney(userDAO.totalWithdrawMoney(1,search.getStartTime(),search.getEndTime()));
//    //待审核提现总额
//    homeTotal.setWaitMoney(userDAO.totalWithdrawMoney(0,search.getStartTime(),search.getEndTime()));
//    //电商平台订单数量
//    userDAO.mallOrderNum(search.getStartTime(),search.getEndTime());
//    //电商平台下单人数
//    userDAO.mallUserNum(search.getStartTime(),search.getEndTime());
//    //电商平台下单金额
//    userDAO.mallAmountTotal(search.getStartTime(),search.getEndTime());
//    //截止上月冻结金额=种植中+提现冻结
//    //截止上月流动资金
//    //种植中的金额
//    //资产总金额=可用+冻结+种植中+未来利息+提现审核中
//    return homeTotal;

}
