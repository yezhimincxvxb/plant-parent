package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MoneyOpEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Data
public class UserMoneyDetail implements Serializable {

    private static final long serialVersionUID = 3763255221751197922L;

    /**
     * 日期
     */
    @JSONField(ordinal = 1)
    private String dateTime;

    /**
     * 资金明细ID
     */
    @JSONField(ordinal = 2)
    private Integer id;

    /**
     * 影响金额
     */
    @JSONField(ordinal = 3)
    private BigDecimal affectMoney;

    /**
     * 影响类型
     */
    @JSONField(ordinal = 4)
    private Integer affectType;

    /**
     * 影响时间
     */
    @JSONField(ordinal = 5,format = "yyyy-MM-dd HH:mm:ss")
    private Date affectTime;

    /**
     * 详情ID
     */
    @JSONField(ordinal = 6)
    private String detailId;

    /**
     * 收入/支出(1：收入；2：支出)
     */
    @JSONField(ordinal = 7)
    private Integer moneyType;

    public Integer getMoneyType() {
        Integer i = this.affectType;
        switch (i) {
            case 12:
            case 15:
            case 16:
            case 17:
                return 1;
            case 4:
            case 13:
            case 14:
                return 2;
            default:
                return 0;
        }
    }

    public String getAffectType() {
        Integer i = this.affectType;
        switch (i) {
            case 1:
                return MoneyOpEnum.RECHARGE.getTypeStr();
            case 2:
                return MoneyOpEnum.RECHARGE_DONE.getTypeStr();
            case 3:
                return MoneyOpEnum.WITHDRAW.getTypeStr();
            case 4:
                return MoneyOpEnum.WITHDRAW_DONE.getTypeStr();
            case 5:
                return MoneyOpEnum.BUY_SEED.getTypeStr();
            case 6:
                return MoneyOpEnum.PLANTED_SEED.getTypeStr();
            case 7:
                return MoneyOpEnum.REAP_SEED_CAPITAL.getTypeStr();
            case 8:
                return MoneyOpEnum.REAP_SEED_PROFIT.getTypeStr();
            case 9:
                return MoneyOpEnum.BUY_CANCEL.getTypeStr();
            case 10:
                return MoneyOpEnum.RECHARGE_FAILED.getTypeStr();
            case 11:
                return MoneyOpEnum.WITHDRAW_FAILED.getTypeStr();
            case 12:
                return MoneyOpEnum.INVITE_AWARD.getTypeStr();
            case 13:
                return MoneyOpEnum.BUY_MALL_PRODUCT.getTypeStr();
            case 14:
                return MoneyOpEnum.BUY_SEED_ORDER.getTypeStr();
            case 15:
                return MoneyOpEnum.SALE_REAP_SEED.getTypeStr();
            case 16:
                return MoneyOpEnum.PANT_SEED_FERTILIZER.getTypeStr();
            case 17:
                return MoneyOpEnum.SALE_REAP_SEED_PROFIT.getTypeStr();
            case 18:
                return MoneyOpEnum.MUSHROOM_COIN.getTypeStr();
            case 19:
                return MoneyOpEnum.RED_PACKAGE.getTypeStr();
            default:
                return "";
        }

    }

    public String getAffectMoney() {
        String result = new DecimalFormat("0.00").format(affectMoney);
        if (result.startsWith("-"))
            return result;

        return "+" + result;
    }
}
