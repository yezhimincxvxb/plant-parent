package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TraceInfoDetail implements Comparable<TraceInfoDetail>{

    @JSONField(ordinal = 1,format = "yyyy.MM.dd HH:mm")
    private Date traceTime;

    @JSONField(ordinal = 2)
    private String traceInfo;

    public TraceInfoDetail(Date traceTime, String traceInfo) {
        this.traceTime = traceTime;
        this.traceInfo = traceInfo;
    }

    public Date getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(Date traceTime) {
        this.traceTime = traceTime;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }


    @Override
    public int compareTo(TraceInfoDetail o) {
        return traceTime.compareTo(o.traceTime);
    }
}
