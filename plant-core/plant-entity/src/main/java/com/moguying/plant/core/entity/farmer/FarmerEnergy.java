package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@TableName("plant_farmer_energy")
@Data
@Accessors(chain = true)
public class FarmerEnergy implements Serializable {
    private static final long serialVersionUID = -1159228886637788678L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private Integer userId;

    /**
     * 增加的生长值
     */
    @TableField
    private Integer incrGrowUpCount;

    /**
     * [0未采摘，1已采摘，2已失效]
     */
    @TableField
    private Integer state;

    /**
     * 增长值来源[1签到，2邀请好友，3每天首次种植，4种植成功]
     */
    @TableField
    private String incrWay;

    /**
     * 添加时间
     */
    @TableField
    private Date addTime;

    /**
     * 用户田园等级
     */
    @TableField(exist = false)
    private Integer farmerLevel;

    /**
     * 查询辅助参数
     */
    @TableField(exist = false)
    private Integer limit;


}