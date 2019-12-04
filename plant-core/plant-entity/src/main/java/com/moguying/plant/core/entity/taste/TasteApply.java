package com.moguying.plant.core.entity.taste;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class TasteApply {

    @Excel(name = "id",orderNum = "1")
    protected String id;

    /**
     * 申请资格的用户id
     */
    @NonNull
    @Excel(name = "用户id",orderNum = "2")
    protected Integer userId;


    @Excel(name = "手机号",orderNum = "3")
    private String phone;

    /**
     * 申请试吃的项
     */
    @NonNull
    protected String tasteId;

    /**
     * 申请的状态
     */
    @NonNull
    @Excel(name = "状态",orderNum = "4",replace = {"未审核_0","已通过_1","未通过_2"})
    protected Integer state;

    /**
     * 用户地址
     */
    protected UserAddress userAddress;


    /**
     * 申请时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间",orderNum = "5",format = "yyyy-MM-dd HH:mm:ss")
    protected Date applyTime;

    /**
     * 试吃商品名
     */
    @Excel(name = "商品名",orderNum = "6")
    protected String productName;


    @JSONField(serializeUsing = BigDecimalSerialize.class)
    protected BigDecimal productPrice;


    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间",orderNum = "7",format = "yyyy-MM-dd HH:mm:ss")
    private Date applyReviewTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间",orderNum = "8",format = "yyyy-MM-dd HH:mm:ss")
    private Date tasteTime;

    @JSONField(serialize = false)
    private Date startTime;

    @JSONField(serialize = false)
    private Date endTime;

    @JSONField(serialize = false)
    private Date tasteStartTime;

    @JSONField(serialize = false)
    private Date tasteEndTime;

}
