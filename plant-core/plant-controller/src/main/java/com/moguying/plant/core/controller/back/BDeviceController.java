package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.service.device.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Api(tags = "设备管理")
public class BDeviceController {

    @Autowired
    private DeviceService deviceService;


    /**
     * 刷新设备网关数据
     * @return
     */
    @PostMapping("/refresh")
    @ApiOperation("刷新设备网关数据")
    public ResponseData<Integer> refreshDevice() {
        ResultData<Integer> resultData = deviceService.curAllData();
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }

}
