package com.moguying.plant.core.entity.account.vo;

import com.moguying.plant.core.entity.account.UserMoneyLog;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DailyMoneyLog implements Serializable {

    private static final long serialVersionUID = -6169274905683273437L;

    private String monthAndDay;

    List<UserMoneyLog> moneyLogs;

}
