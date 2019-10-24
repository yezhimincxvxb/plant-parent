package com.moguying.plant.core.entity.account.vo;

import com.moguying.plant.core.entity.account.UserMoneyLog;
import lombok.Data;

import java.util.List;

@Data
public class DailyMoneyLog {

    private String monthAndDay;

    List<UserMoneyLog> moneyLogs;

}
