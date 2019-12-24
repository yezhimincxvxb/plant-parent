package com.moguying.plant.core.controller.front;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.UserEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
@Slf4j
@Api(tags = "公共接口")
public class FIndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneMessageService messageService;

    @PostMapping(value = "/send/message")
    @ResponseBody
    @ApiOperation("发送短信")
    public ResponseData<SendMessage> seedMessage(@RequestBody SendMessage sendMessage, HttpServletRequest request) {
        if (null == sendMessage.getPhone() || !CommonUtil.INSTANCE.isPhone(sendMessage.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());
        User user = userService.userInfoByPhone(sendMessage.getPhone(), UserEnum.USER_ACTIVE);
        sendMessage.setIsReg(null == user);
        ResultData<Boolean> resultData = messageService.sendCodeMessage(sendMessage);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),
                resultData.getMessageEnum().getState(),
                sendMessage);
    }


}
