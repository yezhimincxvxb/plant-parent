package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.fertilizer.TriggerEvent;
import com.moguying.plant.core.entity.system.Apk;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminActionService;
import com.moguying.plant.core.service.system.ApkService;
import com.moguying.plant.core.service.system.TriggerEventService;
import com.moguying.plant.utils.ApplicationContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/system")
@Api(tags = "系统管理")
@Slf4j
public class BSystemController {

    @Autowired
    private TriggerEventService triggerEventService;

    @Autowired
    private ApkService apkService;

    @Autowired
    private AdminActionService adminActionService;



    @GetMapping("/trigger/event")
    @ApiOperation("触发事件列表")
    public ResponseData<List<TriggerEvent>> triggerEventList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), triggerEventService.triggerEventList());
    }


    /**
     * apk列表
     */
    @PostMapping("/apk")
    @ApiOperation("apk列表")
    public PageResult<Apk> apkList(@RequestBody PageSearch search) {
        return apkService.apkList(search.getPage(), search.getSize(), null);
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


    @PostMapping("/update/controller")
    @ApiOperation("更新控制器")
    public ResponseData<Boolean> updateController() {
        ResultData<Boolean> resultData = adminActionService.generaAction();
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


}
