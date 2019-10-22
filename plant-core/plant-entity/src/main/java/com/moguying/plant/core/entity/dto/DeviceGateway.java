package com.moguying.plant.core.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * plant_device_gateway
 * @author 
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DeviceGateway implements Serializable {
    private Integer id;

    /**
     * 设备网关
     */
    @NonNull
    private String gatewayLogo;

}