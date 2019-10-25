package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@TableName("plant_farmer_info")
public class FarmerInfo implements Serializable {

    private static final long serialVersionUID = 2483461981807422154L;
    /**
     * 用户id
     */
    @NonNull
    @TableId
    private Integer userId;

    /**
     * 用户等级
     */
    @TableField
    private Integer farmerLevel;

    /**
     * 生长值
     */
    @TableField
    private Integer growUpCount;

    /**
     * 是否有等级奖励
     */
    @TableField
    private Boolean levelGift;

}