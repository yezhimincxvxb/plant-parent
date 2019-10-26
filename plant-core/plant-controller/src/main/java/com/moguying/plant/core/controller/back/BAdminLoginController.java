package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminUserService;
import com.moguying.plant.utils.PasswordUtil;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/backEnd")
@Slf4j
public class BAdminLoginController {


    @Autowired
    AdminUserService adminUserService;

    @GetMapping(value = "/image/captcha")
    @NoLogin
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaUtil.out(5,request,response);
    }

    @PostMapping(value = "/login")
    @NoLogin
    public ResponseData<String> login(@RequestBody AdminUser adminUser, HttpServletRequest request){


        if(StringUtils.isEmpty(adminUser.getUserName()) || StringUtils.isEmpty(adminUser.getPassword())
                || StringUtils.isEmpty(adminUser.getCode()))
            return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState(),"");
        //检测code是否相同
        if(!CaptchaUtil.ver(adminUser.getCode(),request)) {
            CaptchaUtil.clear(request);
            return new ResponseData<>(MessageEnum.CODE_ERROR.getMessage(),MessageEnum.CODE_ERROR.getState(),"");
        }

        String password = PasswordUtil.INSTANCE.encode(adminUser.getPassword().getBytes());
        AdminUser user = adminUserService.login(adminUser.getUserName(),password);
        if(user != null) {
            request.getSession().setAttribute(SessionAdminUser.sessionKey,user);
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        }
        return new ResponseData<>(MessageEnum.LOGIN_ERROR.getMessage(),MessageEnum.LOGIN_ERROR.getState(),"");
    }


    @PostMapping(value = "/logout")
    public ResponseData<String> logout(HttpServletRequest request){
        request.setAttribute(SessionAdminUser.sessionKey,null);

        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),"");
    }

}
