package com.moguying.plant.core.entity.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("plant_lottery_log")
public class LotteryLog {

    @TableId(type =  IdType.AUTO)
    private String id;
    //用户id
    @TableField
    private Integer userId;

    //种植id
    @TableField
    private Integer reapId;

    //抽奖时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Integer addTime;

    //抽到类型
    @TableField
    private Integer lotteryType;

    //抽到金额
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField()
    private BigDecimal lotteryAmount;

}
