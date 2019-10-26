package com.moguying.plant.core.service.device;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.device.DeviceGateway;
import com.moguying.plant.core.entity.device.vo.DeviceGatewayData;
import org.apache.axis2.AxisFault;

import java.util.List;

public interface DeviceService {

    ResultData<Integer> curAllData();

    ResultData<List<DeviceGatewayData>> gatewayData(String gatewayLogo);

    PageResult<DeviceGateway> deviceGatewayList(Integer page, Integer size, DeviceGateway deviceGateway);

    String sendService(String method) throws AxisFault;
    String sendService(String method, String gateway) throws AxisFault;


}
