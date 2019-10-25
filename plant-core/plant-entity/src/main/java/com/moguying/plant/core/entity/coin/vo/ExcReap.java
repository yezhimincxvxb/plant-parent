package com.moguying.plant.core.entity.coin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcReap implements Serializable {

    private static final long serialVersionUID = 4392958773591886157L;

    @JSONField(name = "count")
    private Integer excCount;
    @JSONField(name = "address")
    private String extAddress;
    @JSONField(name = "fee")
    private String extFee;
}
