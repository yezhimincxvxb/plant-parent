package com.moguying.plant.core.entity.mall.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

@Data
public class MallOrderDetailVo {

    @Excel(name = "购买数量")
    @JSONField(ordinal = 1)
    private Integer buyCount;

    @Excel(name = "购买总价")
    @JSONField(ordinal = 2, serializeUsing = BigDecimalSerialize.class)
    private Integer buyAmount;

    @Excel(name = "订单流水号")
    @JSONField(ordinal = 3)
    private String orderNumber;

    @Excel(name = "用户姓名")
    @JSONField(ordinal = 4)
    private String realName;

    @Excel(name = "用户手机号")
    @JSONField(ordinal = 5)
    private String phone;

    @Excel(name = "产品名称")
    @JSONField(ordinal = 6)
    private String productName;
}
