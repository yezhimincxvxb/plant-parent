package com.moguying.plant.core.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_reap_fee
 * @author 
 */
@Data
public class ReapFee implements Serializable {

    @Excel(name = "序号")
    private Integer id;

    private Integer userId;

    private String userPhone;

    private String userRealName;

    private Integer inviteUid;

    private String invitePhone;

    private Integer reapId;

    @Excel(name = "结算费用")
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal feeAmount;

    @Excel(name = "状态",replace = {"首种_true","复种_false"})
    private Boolean isFirst;

    @ExcelEntity
    private User user;

    private User inviteUser;

    @ExcelEntity
    private Reap reap;

    //按注册时间查询
    private Date regStartTime;

    private Date regEndTime;

    //按种植时间查询
    private Date startTime;

    private Date endTime;
}