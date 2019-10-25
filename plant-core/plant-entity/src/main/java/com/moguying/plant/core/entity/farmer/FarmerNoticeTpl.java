package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName("plant_farmer_notice_tpl")
@Data
public class FarmerNoticeTpl implements Serializable {
    private static final long serialVersionUID = 944679536295058868L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String triggerEvent;

    @TableField
    private String messageTpl;
}