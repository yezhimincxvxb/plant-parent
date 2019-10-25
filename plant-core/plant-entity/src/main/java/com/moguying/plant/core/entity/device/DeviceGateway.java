package com.moguying.plant.core.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("plant_device_gateway")
public class DeviceGateway implements Serializable {
    private static final long serialVersionUID = -3399619314465443560L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备网关
     */
    @NonNull
    @TableField
    private String gatewayLogo;

}