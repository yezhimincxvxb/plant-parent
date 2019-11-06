package com.moguying.plant.core.entity.coin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcReap implements Serializable {

    private static final long serialVersionUID = 4392958773591886157L;

    private Integer addressId;

    private Integer reapId;
}
