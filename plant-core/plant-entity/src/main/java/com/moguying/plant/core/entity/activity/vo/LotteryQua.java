package com.moguying.plant.core.entity.activity.vo;

import lombok.Data;

@Data
public class LotteryQua {

    //用户id
    private Integer userId;

    //抽奖次数
    private Long lotteryCount;

    //今日抽奖次数
    private Long dailyLotteryCount;
}
