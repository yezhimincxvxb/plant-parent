package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.system.Apk;
import com.moguying.plant.core.entity.fertilizer.TriggerEvent;
import com.moguying.plant.core.service.system.ApkService;
import com.moguying.plant.core.service.system.TriggerEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
@Api(tags = "系统管理")
public class BSystemController {

    @Autowired
    private TriggerEventService triggerEventService;

    @Autowired
    private ApkService apkService;

    @GetMapping("/trigger/event")
    @ApiOperation("触发事件列表")
    public ResponseData<List<TriggerEvent>> triggerEventList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), triggerEventService.triggerEventList());
    }


    /**
     * apk列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/apk")
    @ApiOperation("apk列表")
    public PageResult<Apk> apkList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return apkService.apkList(page, size, null);
    }


    /**
     * 删除apk
     *
     * @param id
     * @return
     */
    @DeleteMapping("/apk/{id}")
    @ApiOperation("删除apk")
    public ResponseData<Integer> deleteApk(@PathVariable Integer id) {
        ResultData<Integer> resultData = apkService.apkDelete(id);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 添加/上架apk
     *
     * @param update
     * @return
     */
    @PutMapping("/apk")
    @ApiOperation("添加/上架apk")
    public ResponseData<Integer> showApk(@RequestBody Apk update) {
        ResultData<Integer> resultData = apkService.saveApk(update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

}
