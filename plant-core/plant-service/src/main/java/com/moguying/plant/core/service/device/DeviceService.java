package com.moguying.plant.core.service.device;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.DeviceGateway;
import com.moguying.plant.core.entity.dto.DeviceGatewayData;
import org.apache.axis2.AxisFault;

import java.util.List;

public interface DeviceService {



    @DataSource
    ResultData<Integer> curAllData();

    ResultData<List<DeviceGatewayData>> gatewayData(String gatewayLogo);

    @DataSource("read")
    PageResult<DeviceGateway> deviceGatewayList(Integer page, Integer size, DeviceGateway deviceGateway);

    String sendService(String method) throws AxisFault;
    String sendService(String method, String gateway) throws AxisFault;


}
