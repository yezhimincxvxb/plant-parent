package com.moguying.plant.core.entity.vo;

import com.moguying.plant.core.entity.dto.UserMoneyLog;
import lombok.Data;

import java.util.List;

@Data
public class DailyMoneyLog {

    private String monthAndDay;

    List<UserMoneyLog> moneyLogs;

}
