package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.admin.vo.AdminUserLogin;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminUserService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.utils.CommonUtil;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@Api(tags = "后台登录")
public class BAdminLoginController {


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private PhoneMessageService phoneMessageService;



    @GetMapping(value = "/image/captcha")
    @NoLogin
    @ApiOperation("图形验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaUtil.out(5, request, response);
    }

    @PostMapping(value = "/login")
    @NoLogin
    @ApiOperation("登录")
    public ResponseData<String> login(@RequestBody @Validated AdminUserLogin userLogin, HttpServletRequest request) {

        if (!phoneMessageService.validateMessage(userLogin.getPhone(), userLogin.getCode()))
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(), MessageEnum.MESSAGE_CODE_ERROR.getState());

        AdminUser user = adminUserService.login(userLogin.getPhone(), CommonUtil.INSTANCE.getClientIp(request));
        if (user != null) {
            request.getSession().setAttribute(SessionAdminUser.sessionKey, user);
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        }
        return new ResponseData<>(MessageEnum.LOGIN_ERROR.getMessage(), MessageEnum.LOGIN_ERROR.getState(), "");
    }


    @PostMapping(value = "/logout")
    @ApiOperation("退出")
    public ResponseData<String> logout(HttpServletRequest request) {
        request.setAttribute(SessionAdminUser.sessionKey, null);

        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), "");
    }

}
