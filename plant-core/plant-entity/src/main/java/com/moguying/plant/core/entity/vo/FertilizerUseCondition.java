package com.moguying.plant.core.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FertilizerUseCondition {

    //商城购物时的订单id
    //因为商城购物时生成的是订单id,一个订单可能有多个商品，所以在使用时，传入的是mallOrderId
    //而在判断时使用productId严格匹配对应商品，如果是获取通用的商城券，productId 传0
    private Integer mallOrderId;

    //商品id
    private Integer productId;

    //用户id
    private Integer userId;

    //棚id
    private Integer blockId;

    //购买菌包订单id
    private Integer seedOrderId;

    //菌包类型
    private Integer seedTypeId;

    //使用时的金额
    private BigDecimal amount;

    //有效时间
    private Date expireTime;

    //券状态
    private Integer state;

    //券类型
    private Integer type;

    //券类型list
    private List<Integer> types;
}
