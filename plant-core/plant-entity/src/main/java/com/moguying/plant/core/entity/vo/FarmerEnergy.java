package com.moguying.plant.core.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_farmer_energy
 * @author 
 */
@Data
public class FarmerEnergy implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 增加的生长值
     */
    private Integer incrGrowUpCount;

    /**
     * [0未采摘，1已采摘，2已失效]
     */
    private Integer state;

    /**
     * 增长值来源[1签到，2邀请好友，3每天首次种植，4种植成功]
     */
    private String incrWay;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 用户田园等级
     */
    private Integer farmerLevel;

    /**
     * 查询辅助参数
     */
    private Integer limit;


}