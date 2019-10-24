package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.constant.UserEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.InnerMessage;
import com.moguying.plant.core.entity.dto.PhoneMessage;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.dto.UserInvite;
import com.moguying.plant.core.entity.vo.ForgetPassword;
import com.moguying.plant.core.entity.vo.Login;
import com.moguying.plant.core.entity.vo.LoginResponse;
import com.moguying.plant.core.entity.vo.Register;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserInviteService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CommonUtil;
import com.wf.captcha.Captcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/api")
public class ARegisterController {

    @Autowired
    private PhoneMessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInviteService userInviteService;





    /**
     * 注册
     * @param register
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseData<LoginResponse> register(@RequestBody Register register){
        if(!CommonUtil.INSTANCE.isPhone(register.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(),MessageEnum.PHONE_ERROR.getState());
       User user = userService.userInfoByPhone(register.getPhone(),null);
       User addUser = new User();
       //用户是否已存在
       if(user != null)
           return new ResponseData<>(MessageEnum.PHONE_EXISTS.getMessage(),MessageEnum.PHONE_EXISTS.getState());
        addUser.setPhone(register.getPhone());
       //校验短信验证码
       if(StringUtils.isEmpty(register.getMsgCode()))
           return new ResponseData<>(MessageEnum.MESSAGE_CODE_IS_EMPTY.getMessage(),MessageEnum.MESSAGE_CODE_IS_EMPTY.getState());
       PhoneMessage message = messageService.messageByPhone(register.getPhone());
       if(message == null || !message.getCode().equals(register.getMsgCode()))
           return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(),MessageEnum.MESSAGE_CODE_ERROR.getState());
       messageService.setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);
       //校验邀请人
       if(!StringUtils.isEmpty(register.getInviteCode())) {
           User inviteUser = userService.userInfoByPhone(register.getInviteCode(),null);
            if(inviteUser == null)
                return new ResponseData<>(MessageEnum.INVITE_USER_NOT_EXISTS.getMessage(),MessageEnum.INVITE_USER_NOT_EXISTS.getState());
           addUser.setInviteUid(inviteUser.getId());
       }
       addUser.setRegSource(UserEnum.REG_SOURCE_MOBILE.getStateName());
       ResultData<TriggerEventResult<InnerMessage>> resultData = userService.addUser(addUser);
       //如果邀请人不为空
       if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)){
           if (addUser.getInviteUid() != null) {
               UserInvite invite = new UserInvite();
               invite.setUserId(addUser.getId());
               invite.setPhone(addUser.getPhone());
               invite.setInviteUserId(addUser.getInviteUid());
               userInviteService.saveInviteInfo(invite);
           }

           return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(),
                   userService.loginSuccess(addUser.getId(), addUser.getPhone()).getData().getData());
       }
       return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 登录
     * @param login
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseData<LoginResponse> login(@RequestBody Login login, HttpServletRequest request){
        if(null != request.getSession().getAttribute("user"))
            return new ResponseData<>(MessageEnum.USER_HAS_LOGIN.getMessage(),MessageEnum.USER_HAS_LOGIN.getState());

        if (StringUtils.isEmpty(login.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());
        User user;
        //验证码登录
        if (!StringUtils.isEmpty(login.getCode()) && StringUtils.isEmpty(login.getPassword())) {
            PhoneMessage message = messageService.messageByPhone(login.getPhone());
            if(message == null || !message.getCode().equals(login.getCode()))
                return new ResponseData<>(MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getMessage(),MessageEnum.MESSAGE_CODE_LOGIN_ERROR.getState());
            messageService.setMessageState(message.getId(),SystemEnum.PHONE_MESSAGE_VALIDATE);
            user = userService.userInfoByPhone(login.getPhone(),UserEnum.USER_ACTIVE);
        } else if (!StringUtils.isEmpty(login.getPassword()) && StringUtils.isEmpty(login.getCode())) { //密码登录
            user = userService.loginByPhoneAndPassword(login.getPhone(),login.getPassword());
            if(user == null)
                return new ResponseData<>(MessageEnum.LOGIN_ERROR.getMessage(),MessageEnum.LOGIN_ERROR.getState());
        } else {
            return new ResponseData<>(MessageEnum.LOGIN_METHOD_ERROR.getMessage(),MessageEnum.LOGIN_METHOD_ERROR.getState());
        }
        User update = new User();
        update.setLastLoginTime(new Date());
        if(null != userService.saveUserInfo(user.getId(),update).getData()) {
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                    userService.loginSuccess(user.getId(),user.getPhone()).getData().getData());
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }






    /**
     * 忘记密码
     * @param forgetPassword
     * @return
     */
    @PutMapping(value = "/forget")
    @ResponseBody
    public ResponseData<Integer> forgetPassword(@RequestBody ForgetPassword forgetPassword){
        if(!CommonUtil.INSTANCE.isPhone(forgetPassword.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(),MessageEnum.PHONE_ERROR.getState());
        User user = userService.userInfoByPhone(forgetPassword.getPhone(),UserEnum.USER_ACTIVE);
        if(user == null)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(),MessageEnum.USER_NOT_EXISTS.getState());
        User update = new User();
        if(messageService.validateMessage(forgetPassword.getPhone(),forgetPassword.getCode()) <= 0 )
            return new ResponseData<>(MessageEnum.MESSAGE_CODE_ERROR.getMessage(),MessageEnum.MESSAGE_CODE_ERROR.getState());
        update.setPassword(forgetPassword.getPassword());
        ResultData<User> resultData = userService.saveUserInfo(user.getId(),update);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData().getId());
    }

    /**
     * 图形验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/image/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 设置请求头为输出图片类型
            CaptchaUtil.setHeader(response);

            // 三个参数分别为宽、高、位数
            GifCaptcha gifCaptcha = new GifCaptcha(180, 65, 4);

            // 设置字体
            gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

            // 设置类型，纯数字、纯字母、字母数字混合
            gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

            // 验证码存入session
            request.getSession().setAttribute("captcha", gifCaptcha.text().toLowerCase());

            // 输出图片流
            gifCaptcha.out(response.getOutputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}
