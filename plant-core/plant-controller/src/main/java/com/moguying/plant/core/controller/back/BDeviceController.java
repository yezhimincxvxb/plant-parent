package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class BDeviceController {

    @Autowired
    private DeviceService deviceService;


    /**
     * 刷新设备网关数据
     * @return
     */
    @PostMapping("/refresh")
    public ResponseData<Integer> refreshDevice() {
        ResultData<Integer> resultData = deviceService.curAllData();
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }

}
