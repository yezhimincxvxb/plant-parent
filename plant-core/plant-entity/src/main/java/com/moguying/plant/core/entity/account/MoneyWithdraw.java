package com.moguying.plant.core.entity.account;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BankCarSerialize;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("plant_money_withdraw")
public class MoneyWithdraw implements Serializable {

    private static final long serialVersionUID = -1499769342080698737L;

    @Excel(name = "序号")
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer userId;

    /**
     * 用户姓名
     */
    @Excel(name = "真实姓名")
    @JSONField(ordinal = 3)
    @TableField(exist = false)
    private String realName;

    /**
     * 手机号
     */
    @Excel(name = "预留手机")
    @JSONField(ordinal = 4)
    @TableField
    private String bankPhone;

    /**
     * 到账银行卡号
     */
    @Excel(name = "到账银行卡号")
    @JSONField(ordinal = 5, serializeUsing = BankCarSerialize.class)
    @TableField
    private String bankNumber;

    /**
     * 提现流水号
     */
    @Excel(name = "流水号")
    @JSONField(ordinal = 6)
    @TableField
    private String orderNumber;

    /**
     * 提现短信流水号
     */
    @JSONField(ordinal = 7)
    @TableField
    private String seqNo;

    /**
     * 提现金额
     */
    @Excel(name = "提现金额")
    @JSONField(ordinal = 8, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal withdrawMoney;

    /**
     * 手续费
     */
    @Excel(name = "手续费")
    @JSONField(ordinal = 9, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal fee;

    /**
     * 到账金额
     */
    @Excel(name = "到账金额")
    @JSONField(ordinal = 10, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal toAccountMoney;

    /**
     * 提现时间
     */
    @Excel(name = "提现时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date withdrawTime;

    /**
     * 审核用户
     */
    @JSONField(ordinal = 12)
    @TableField
    private Integer verifyUser;

    /**
     * 审核备注
     */
    @JSONField(ordinal = 13)
    @TableField
    private String verifyMark;

    /**
     * [0未审核，1审核通过，2审核未通，3已到账，4到账中]
     */
    @Excel(name = "状态", replace = {"未审核_0", "审核通过_1", "审核未通_2", "已到账_3", "到账中_4"})
    @JSONField(ordinal = 14)
    @TableField
    private Integer state;

    @TableField(exist = false)
    private transient List<Integer> inState;

    /**
     * 审核时间
     */
    @Excel(name = "审核时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 15, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date verifyTime;

    /**
     * 到账时间
     */
    @Excel(name = "到账时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(ordinal = 16, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date successTime;


    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Date startTime;

    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Date endTime;
}