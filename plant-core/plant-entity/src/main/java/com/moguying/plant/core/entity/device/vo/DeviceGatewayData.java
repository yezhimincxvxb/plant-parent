package com.moguying.plant.core.entity.device.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceGatewayData implements Serializable {

    private static final long serialVersionUID = 592984561769012585L;

    @JSONField(ordinal = 1)
    private String dataTime;

    @JSONField(ordinal = 2)
    private String gatewayLogo;

    @JSONField(ordinal = 3)
    private String sensorName;

    @JSONField(ordinal = 4)
    private String channelName;

    @JSONField(ordinal = 5)
    private String value;
}
