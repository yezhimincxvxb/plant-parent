package com.moguying.plant.core.service.user;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.constant.UserEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.vo.LoginResponse;
import com.moguying.plant.core.entity.vo.UserSummaryInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    //用户列表
    @DataSource("read")
    PageResult<User> userList(Integer page, Integer limit, User where);

    //添加用户
    @DataSource("write")
    ResultData<TriggerEventResult<InnerMessage>> addUser(User user);

    //Id查找用户信息
    @DataSource("read")
    User userInfoById(Integer id);


    //phone查找用户信息
    @DataSource("read")
    User userInfoByPhone(String phone, UserEnum userEnum);

    @DataSource("read")
    User userInfoByInviteCodeAndId(Integer userId, String inviteCode);

    @DataSource("write")
    ResultData<Integer> saveBankCard(UserBank bank);

    @DataSource("write")
    ResultData<Integer> deleteCard(Integer id);

    @DataSource("read")
    List<UserBank> bankCardList(Integer userId);

    @DataSource("read")
    UserBank bankCard(Integer userId, Integer id);

    @DataSource("read")
    UserBank bankCardByOrderNumber(String orderNumber);

    @DataSource("write")
    ResultData<Integer> addAddress(UserAddress address);

    @DataSource("read")
    List<UserAddress> addressList(Integer userId);

    @DataSource("write")
    ResultData<Integer> updateAddress(Integer id, UserAddress address);

    @DataSource("write")
    ResultData<Integer> deleteAddress(UserAddress address);

    @DataSource("write")
    ResultData<User> saveUserInfo(Integer id, User user);

    @DataSource("read")
    User loginByPhoneAndPassword(String phone, String password);

    @DataSource("read")
    UserSummaryInfo userSummaryInfo(User user);

    @DataSource("read")
    PageResult<UserMessage> userMessageList(Integer page, Integer size, Integer userId);

    @DataSource("read")
    UserAddress userDefaultAddress(Integer userId);

    @DataSource("read")
    UserAddress userAddressByIdAndUserId(Integer id, Integer userId, Boolean isDelete);

    @DataSource("write")
    ResultData<TriggerEventResult<LoginResponse>> loginSuccess(Integer id, String phone);


    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<User> search, HttpServletRequest request);

    @DataSource("read")
    User userInfo(User where);

}
