package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SendPayOrder implements Serializable {


    private static final long serialVersionUID = -8269633466326566773L;
    @JSONField(ordinal = 1)
    private Boolean isCheck;

    @JSONField(serialize = false)
    private String payPassword;

    @JSONField(serialize = false)
    private String payMsgCode;

    @JSONField(serialize = false)
    private String seqNo;

    @JSONField(ordinal = 2)
    private Integer orderId;

    @JSONField(serialize = false)
    private String authMsg;

    @JSONField(serialize = false)
    private Integer bankId;

    @JSONField(serialize = false)
    private List<Integer> fertilizerIds;

}
