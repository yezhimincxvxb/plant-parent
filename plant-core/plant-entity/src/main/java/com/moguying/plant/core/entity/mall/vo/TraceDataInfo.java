package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TraceDataInfo implements Serializable , Comparable<TraceDataInfo>{

    private static final long serialVersionUID = -4475601683393624059L;

    /**
     *物流内容
     */
    private String context;

    /**
     *原始时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     *格式化时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date ftime;

    /**
     *签收状态
     */
    private String status;

    /**
     *行政区域的编码
     */
    private String areaCode;

    /**
     *行政区域的名称
     */
    private String areaName;


    @Override
    public int compareTo(TraceDataInfo o) {
        return o.getFtime().compareTo(this.ftime);
    }
}
