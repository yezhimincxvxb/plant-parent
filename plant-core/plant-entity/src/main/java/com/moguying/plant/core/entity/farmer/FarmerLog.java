package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_farmer_log")
@Data
public class FarmerLog implements Serializable {
    private static final long serialVersionUID = -1485285374561657875L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField
    private Integer userId;

    /**
     * 增长的成长值
     */
    @TableField
    private Integer incrGrowUpCount;

    /**
     * 增长后的值
     */
    @TableField
    private Integer growUpCount;

    /**
     * 添加时间
     */
    @TableField
    private Date addTime;

    /**
     * 成长方式
     */
    @TableField
    private String incrWay;

}