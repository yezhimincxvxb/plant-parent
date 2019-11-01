package com.moguying.plant.core.entity.taste;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class TasteApply {

    private String id;

    /**
     * 申请资格的用户id
     */
    @NonNull
    private Integer userId;

    @JSONField(serializeUsing = IdCardSerialize.class)
    private String phone;

    /**
     * 申请试吃的项
     */
    @NonNull
    private String tasteId;

    /**
     * 申请的状态
     */
    @NonNull
    private Integer state;

    /**
     * 用户地址
     */
    private UserAddress userAddress;


    /**
     * 申请时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 试吃商品名
     */
    private String productName;


}
