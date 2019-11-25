package com.moguying.plant.core.entity.taste;

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

    protected String id;

    /**
     * 申请资格的用户id
     */
    @NonNull
    protected Integer userId;


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
    protected Integer state;

    /**
     * 用户地址
     */
    protected UserAddress userAddress;


    /**
     * 申请时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date applyTime;

    /**
     * 试吃商品名
     */
    protected String productName;


    @JSONField(serializeUsing = BigDecimalSerialize.class)
    protected BigDecimal productPrice;


    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyReviewTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
