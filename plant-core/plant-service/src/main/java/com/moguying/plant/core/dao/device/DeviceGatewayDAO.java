package com.moguying.plant.core.dao.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.DeviceGateway;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeviceGatewayDAO继承基类
 */
@Repository
public interface DeviceGatewayDAO extends BaseMapper<DeviceGateway> {
    DeviceGateway selectByGatewayLogo(String gatewayLogo);
    List<DeviceGateway> selectSelective(DeviceGateway where);
}