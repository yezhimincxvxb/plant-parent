package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.config.MybatisPlusConfig;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.entity.user.UserBank;
import com.moguying.plant.core.entity.user.dto.UserPlantMoneyDto;
import com.moguying.plant.core.entity.user.vo.UserPlantMoneyVo;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class BUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    @ApiOperation("用户列表")
    public PageResult<User> userList(@RequestBody PageSearch<User> search) {
        if (search.getWhere() == null) search.setWhere(new User());
        return userService.userList(search.getPage(), search.getSize(), search.getWhere());
    }


    @PostMapping("/excel")
    @ApiOperation("用户列表下载")
    public ResponseData<Integer> excelList(@SessionAttribute(SessionAdminUser.sessionKey) AdminUser user,
                                           @RequestBody PageSearch<User> search, HttpServletRequest request) {
        if (Objects.isNull(search.getWhere())) search.setWhere(new User());
        userService.downloadExcel(user.getId(), search, request);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
    }


    @PostMapping(value = "/add")
    @ApiOperation("添加用户")
    public ResponseData<Integer> userAdd(@RequestBody User addUser) {
        if (addUser.getPhone() != null && !CommonUtil.INSTANCE.isPhone(addUser.getPhone()))
            return new ResponseData<>(MessageEnum.PHONE_ERROR.getMessage(), MessageEnum.PHONE_ERROR.getState());
        ResultData<TriggerEventResult<InnerMessage>> resultData = userService.addUser(addUser);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    @GetMapping(value = "/{id}")
    @ApiOperation("用户信息")
    public ResponseData<User> userInfo(@PathVariable Integer id) {
        if (id <= 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        User where = new User();
        where.setId(id);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), userService.userInfo(where));
    }


    @PutMapping(value = "/{id}")
    @ApiOperation("编辑用户")
    public ResponseData<Integer> updateInfo(@RequestBody User user, @PathVariable Integer id) {
        ResultData<User> resultData = userService.saveUserInfo(id, user);
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData().getId());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    @PostMapping(value = "/add/address")
    @ApiOperation("用户添加地址")
    public ResponseData<Integer> userAddAddress(@RequestBody UserAddress address) {
        ResultData<Integer> resultData = userService.addAddress(address);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    @GetMapping(value = "/address/{userId}")
    @ApiOperation("用户地址列表")
    public ResponseData<List<UserAddress>> userAddressList(@PathVariable Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), userService.addressList(userId));
    }


    @GetMapping("/bank/{userId}")
    @ApiOperation("用户银行卡列表")
    public ResponseData<List<UserBank>> userBankList(@PathVariable Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), userService.bankCardList(userId));
    }

    @PostMapping("/plant/money/list")
    @ApiOperation("种植金额列表")
    public PageResult<UserPlantMoneyVo> userPlantMoneyList(@RequestBody PageSearch<UserPlantMoneyDto> search) {
        return userService.userPlantMoneyList(search.getPage(), search.getSize(), search.getWhere());
    }

}
