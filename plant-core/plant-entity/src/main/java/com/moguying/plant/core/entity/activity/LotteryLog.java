package com.moguying.plant.core.entity.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("plant_activity_lottery_log")
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
    private Date addTime;

    //抽到类型
    @TableField
    private Integer lotteryType;

    //抽到金额
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal lotteryAmount;

    //种植份数
    @TableField(exist = false)
    private Integer plantCount;

    //手机号
    @TableField(exist = false)
    private String phone;

    //查询抽奖开始时间
    @TableField(exist = false)
    private Date startTime;

    //查询抽奖结束时间
    @TableField(exist = false)
    private Date endTime;

}
