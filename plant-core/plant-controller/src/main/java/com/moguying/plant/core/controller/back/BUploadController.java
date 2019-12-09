package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.upload.GetTokenRequest;
import com.moguying.plant.core.entity.upload.UploadTokenVo;
import com.moguying.plant.core.entity.upload.UploadVo;
import com.moguying.plant.core.service.feedback.material.FeedbackMaterialService;
import com.moguying.plant.core.service.upload.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
@Slf4j
@Api(tags = "文件上传")
public class BUploadController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private FeedbackMaterialService feedbackMaterialService;

    @PostMapping(value = "/uploadImg")
    @ApiOperation("上传图片")
    @NoLogin
    public ResponseData<UploadVo> seedPicAdd(HttpServletRequest request) {
        UploadVo uploadVo = uploadService.upload(request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), uploadVo);
    }


    @PostMapping(value = "/getToken")
    @ApiOperation("获取Token")
    @NoLogin
    public ResponseData<UploadTokenVo> getToken() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), uploadService.getToken());
    }


    @PostMapping(value = "/check")
    @ApiOperation("素材去重")
    @NoLogin
    public ResponseData<Integer> check(@RequestBody GetTokenRequest request) {
        ResultData<Boolean> resultData = feedbackMaterialService.checkMaterial(request.getFileName());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


}
