package com.moguying.plant.core.service.user;

import com.moguying.plant.constant.UserEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.entity.user.UserBank;
import com.moguying.plant.core.entity.user.UserMessage;
import com.moguying.plant.core.entity.user.vo.LoginResponse;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import com.moguying.plant.core.entity.user.vo.UserSummaryInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    //用户列表
    PageResult<User> userList(Integer page, Integer limit, User where);

    //添加用户
    ResultData<TriggerEventResult<InnerMessage>> addUser(User user);

    //Id查找用户信息
    User userInfoById(Integer id);

    //phone查找用户信息
    User userInfoByPhone(String phone, UserEnum userEnum);

    User userInfoByInviteCode(String inviteCode, UserEnum userEnum);

    User userInfoByInviteCodeAndId(Integer userId, String inviteCode);

    ResultData<Integer> saveBankCard(UserBank bank);

    ResultData<Integer> deleteCard(Integer id);

    List<UserBank> bankCardList(Integer userId);

    UserBank bankCard(Integer userId, Integer id);

    UserBank bankCardByOrderNumber(String orderNumber);

    ResultData<Integer> addAddress(UserAddress address);

    List<UserAddress> addressList(Integer userId);

    ResultData<Integer> updateAddress(Integer id, UserAddress address);

    ResultData<Integer> deleteAddress(UserAddress address);

    ResultData<User> saveUserInfo(Integer id, User user);

    User loginByPhoneAndPassword(String phone, String password);

    UserSummaryInfo userSummaryInfo(User user);

    PageResult<UserMessage> userMessageList(Integer page, Integer size, Integer userId);

    UserAddress userDefaultAddress(Integer userId);

    UserAddress userAddressByIdAndUserId(Integer id, Integer userId, Boolean isDelete);

    ResultData<TriggerEventResult<LoginResponse>> loginSuccess(Integer id, String phone);

    void downloadExcel(Integer userId, PageSearch<User> search, HttpServletRequest request);

    User userInfo(User where);

    List<UserActivityLogVo> inviteUser(Integer userId);

    ResultData<Integer> pickUpReward(Integer userId, Integer id);
}
