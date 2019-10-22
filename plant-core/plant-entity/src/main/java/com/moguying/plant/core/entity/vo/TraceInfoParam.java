package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TraceInfoParam implements Serializable {

    private static final long serialVersionUID = -7788059497565529175L;

    /**
     *查询的快递公司的编码，一律用小写字母
     */
    @NotBlank(message = "编码必须")
    @JSONField(ordinal = 1)
    private String com;

    /**
     *查询的快递单号， 单号的最大长度是32个字符
     */
    @NotBlank(message = "快递单号必填")
    @JSONField(ordinal = 2)
    private String num;

    /**
     *收件人或寄件人的手机号或固话（顺丰单号必填，也可以填写后四位，如果是固话，请不要上传分机号）
     */
    @JSONField(ordinal = 3)
    private String phone;

    /**
     *出发地城市，省-市-区
     */
    @JSONField(ordinal = 4)
    private String from;

    /**
     *目的地城市，省-市-区
     */
    @JSONField(ordinal = 5)
    private String to;

    /**
     *添加此字段表示开通行政区域解析功能。0：关闭（默认），1：开通行政区域解析功能，
     * 2：开通行政解析功能并且返回出发、目的及当前城市信息
     */
    @JSONField(ordinal = 6)
    private Integer resultv2;

    public TraceInfoParam setCom(String com) {
        this.com = com;
        return this;
    }

    public TraceInfoParam setNum(String num) {
        this.num = num;
        return this;
    }

    public TraceInfoParam setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public TraceInfoParam setFrom(String from) {
        this.from = from;
        return this;
    }

    public TraceInfoParam setTo(String to) {
        this.to = to;
        return this;
    }

    public TraceInfoParam setResultv2(Integer resultv2) {
        this.resultv2 = resultv2;
        return this;
    }
}
