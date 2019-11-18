package com.moguying.plant.core.dao.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.device.DeviceGateway;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeviceGatewayDAO继承基类
 */
@Repository
public interface DeviceGatewayDAO extends BaseDAO<DeviceGateway> {
    DeviceGateway selectByGatewayLogo(String gatewayLogo);

    List<DeviceGateway> selectSelective(DeviceGateway where);
}