package com.moguying.plant.core.entity.taste;

import com.moguying.plant.core.entity.user.UserAddress;
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


    private UserAddress userAddress;


    /**
     * 申请时间
     */
    private Date applyTime;


}
