package com.moguying.plant.core.entity.reap;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_reap_fee
 *
 * @author
 */
@TableName("plant_reap_fee")
@Data
public class ReapFee implements Serializable {

    private static final long serialVersionUID = 3766778294029886224L;

    @Excel(name = "序号")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private Integer userId;

    @TableField(exist = false)
    private String userPhone;

    @TableField(exist = false)
    private String userRealName;

    @TableField
    private Integer inviteUid;

    @TableField(exist = false)
    private String invitePhone;

    @TableField
    private Integer reapId;

    @Excel(name = "结算费用")
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal feeAmount;

    @Excel(name = "状态", replace = {"首种_true", "复种_false"})
    @TableField
    private Boolean isFirst;

    @Excel(name = "审核状态",replace = {"已审核_true","未审核_false"})
    @TableField
    private Boolean state;


    @ExcelEntity
    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private User inviteUser;

    @ExcelEntity
    @TableField(exist = false)
    private Reap reap;

    //按注册时间查询
    @TableField(exist = false)
    private Date regStartTime;

    @TableField(exist = false)
    private Date regEndTime;

    //按种植时间查询
    @TableField(exist = false)
    private Date startTime;

    @TableField(exist = false)
    private Date endTime;
}