package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DeviceGatewayData {

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
