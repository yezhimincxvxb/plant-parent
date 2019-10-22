package com.moguying.plant.core.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_farmer_log
 * @author 
 */
@Data
public class FarmerLog implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 增长的成长值
     */
    private Integer incrGrowUpCount;

    /**
     * 增长后的值
     */
    private Integer growUpCount;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 成长方式
     */
    private String incrWay;

}