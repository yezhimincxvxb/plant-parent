package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class TraceInfo {

    /**
     * 公司名称
     */
    @JSONField(ordinal = 1)
    private String companyName;

    /**
     * 快递单号
     */
    @JSONField(ordinal = 2)
    private String traceNumber;

    /**
     *公司电话
     */
    @JSONField(ordinal = 3)
    private String companyPhone;

    /**
     * 状态详情
     */
    @JSONField(ordinal = 4)
    private String stateInfo;

    /**
     * 快递详情
     */
    @JSONField(ordinal = 5)
    private List<TraceInfoDetail> traceDetail;

}
