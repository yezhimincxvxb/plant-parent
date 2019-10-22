package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/backEnd/user")
public class BUserController {

    @Autowired
    UserService userService;

    /**
     * 用户列表
     * @param search
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public PageResult<User> userList(@RequestBody PageSearch<User> search) {
        return userService.userList(search.getPage(),search.getSize(),search.getWhere());
    }


    /**
     * 用户列表下载
     * @param user
     * @param search
     * @param request
     * @return
     */
    @PostMapping("/excel")
    @ResponseBody
    public ResponseData<Integer> excelList(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                           @RequestBody PageSearch<User> search, HttpServletRequest request) {
        if(Objects.isNull(search.getWhere())) search.setWhere(new User());
        userService.downloadExcel(user.getId(),search,request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
    }



    /**
     * 添加用户
     * @param addUser
     * @return
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseData<Integer> userAdd(@RequestBody User addUser){
        if(addUser.getPhone() != null && !CommonUtil.INSTANCE.isPhone(addUser.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(),MessageEnum.PHONE_ERROR.getState());

        ResultData<TriggerEventResult<InnerMessage>> resultData = userService.addUser(addUser);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<User> userInfo(@PathVariable Integer id){
        if(id <= 0 )
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(),MessageEnum.PARAMETER_ERROR.getState());
        User where = new User();
        where.setId(id);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),userService.userInfo(where));
    }


    /**
     * 编辑用户信息
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<Integer> updateInfo(@RequestBody User user, @PathVariable Integer id){
        ResultData<User> resultData = userService.saveUserInfo(id,user);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData().getId());
    }





    /**
     * 用户添加地址
     * @param address
     * @return
     */
    @PostMapping(value = "/add/address")
    @ResponseBody
    public ResponseData<Integer> userAddAddress(@RequestBody UserAddress address){
        ResultData<Integer> resultData = userService.addAddress(address);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 用户地址列表
     * @param userId
     * @return
     */
    @GetMapping(value = "/address/{userId}")
    @ResponseBody
    public ResponseData<List<UserAddress>> userAddressList(@PathVariable Integer userId){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),userService.addressList(userId));
    }


    /**
     * 用户银行卡列表
     * @param userId
     * @return
     */
    @GetMapping("/bank/{userId}")
    @ResponseBody
    public ResponseData<List<UserBank>> userBankList(@PathVariable Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),userService.bankCardList(userId));
    }




}
