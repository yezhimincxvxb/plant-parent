package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminAction;
import com.moguying.plant.core.service.admin.AdminActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/action")
@Api(tags = "权限管理")
public class BAdminActionController {


    @Autowired
    private AdminActionService adminActionService;

    @PostMapping("/update")
    @ApiOperation("更新权限")
    public ResponseData<Boolean> updateController() {
        ResultData<Boolean> resultData = adminActionService.generaAction();
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }

    @GetMapping("/tree")
    @ApiOperation("权限树")
    public ResponseData<Map<String, List<AdminAction>>> actionTree(){
        ResultData<Map<String, List<AdminAction>>> mapResultData = adminActionService.actionTree();
        return new ResponseData<>(mapResultData.getMessageEnum().getMessage(),mapResultData.getMessageEnum().getState(),mapResultData.getData());
    }


}
