package com.moguying.plant.core.entity.seed;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_seed
 *
 * @author
 */
@TableName("plant_seed")
@Data
public class Seed implements Serializable {

    private static final long serialVersionUID = -1725744886920886061L;


    @Excel(name = "序号")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 种子名称
     */
    @Excel(name = "菌包名称")
    @TableField
    private String name;

    /**
     * 副标题
     */
    @JSONField
    @TableField
    private String smallName;

    /**
     * 单价
     */
    @Excel(name = "菌包单价")
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal perPrice;

    /**
     * 总价
     */
    @Excel(name = "菌包总价")
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal totalAmount;

    /**
     * 生长周期
     */
    @Excel(name = "生长周期")
    @JSONField
    @TableField
    private Integer growDays;

    /**
     * 种子编号
     */
    @TableField
    private String serialNumber;

    /**
     * 利率
     */
    @Excel(name = "利率")
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal interestRates;


    /**
     * 种子对应的品种
     */
    @TableField
    private Integer seedType;

    /**
     * 种子类型名称
     */
    @TableField(exist = false)
    private String seedTypeName;

    /**
     * 总份数
     */
    @Excel(name = "菌包总份数")
    @TableField
    private Integer totalCount;

    /**
     * 剩余份数
     */
    @TableField
    private Integer leftCount;

    /**
     * 购买时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField
    private Date openTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField
    private Date closeTime;

    /**
     * 图片相册id,多个以,相隔
     */
    @TableField
    private String picIds;

    /**
     * 图片地址
     */
    @TableField(exist = false)
    private String picUrl;

    /**
     * 种子状态[0待审核,1可种植已审核状态,3已取消]
     */
    @TableField
    private Integer state;

    /**
     * 是否上架[0未上架，1已上架]
     */
    @TableField
    private Boolean isShow;

    /**
     * 在多少级别的棚区种植
     */
    @TableField
    private Integer plantLevel;

    /**
     * 收获类型[1,按月收获，2，到期收获]
     */
    @TableField
    private Integer reapType;

    /**
     * 种植类型[1:成交日种植，2:T+1种植，3:T+2种植,4:成立时种植]
     */
    @Excel(name = "种植类型", replace = {"成交日种植_1", "T+1种植_2", "T+2种植_3", "成立时种植_4"})
    @TableField
    private Integer plantType;

    /**
     * 审核备注
     */
    @TableField
    private String reviewMark;

    /**
     * 添加的用户id
     */
    @TableField
    private Integer addUid;

    /**
     * 审核用户
     */
    @TableField
    private Integer reviewUid;

    /**
     * 审核时间
     */
    @TableField
    private Date reviewTime;

    /**
     * 添加时间
     */
    @Excel(name = "添加时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 预计收益
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal totalInterest;

    /**
     * 收获的成本
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal reapAmount;

    /**
     * 收获的收益
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal reapInterest;

    /**
     * 内购份数
     */
    @TableField
    private Integer innerCount;

    /**
     * 实际订单金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal realAmount;


    /**
     * 实际订单收益
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal realInterest;

    @TableField(exist = false)
    private SeedContent content;

    /**
     * 排序
     */
    @TableField
    private Integer orderNumber;

    /**
     * 售罄时间
     */
    @Excel(name = "售罄时间", format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date fullTime;


    @TableField
    private Boolean isHot;


    /**
     * 菌包分类信息
     */
    @TableField(exist = false)
    private SeedType typeInfo;
}