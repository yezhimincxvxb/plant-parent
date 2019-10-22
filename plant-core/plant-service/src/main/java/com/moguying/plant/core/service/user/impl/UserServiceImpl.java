package com.moguying.plant.core.service.user.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.constant.ApiEnum;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.constant.UserEnum;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.dao.user.UserBankDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.dao.user.UserMessageDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.vo.LoginResponse;
import com.moguying.plant.core.entity.vo.UserSummaryInfo;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserBankDAO userBankDAO;

    @Autowired
    private UserAddressDAO addressDAO;

    @Autowired
    private UserMoneyDAO userMoneyDAO;

    @Autowired
    private SeedOrderDAO seedOrderDAO;

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private UserMessageDAO userMessageDAO;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${excel.download.dir}")
    private String downloadDir;


    @Override
    @Pagination
    @DataSource("read")
    public PageResult<User> userList(Integer page, Integer limit, User where) {
        userDAO.selectSelective(where);
        return null;
    }

    @TriggerEvent(action = "register")
    @Override
    @DataSource("write")
    public ResultData<TriggerEventResult<InnerMessage>> addUser(User user) {
        ResultData<TriggerEventResult<InnerMessage>> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (user.getPhone() != null) {
            User where = new User();
            where.setPhone(user.getPhone());
            if (userDAO.selectSelective(where).size() > 0) {
                return resultData.setMessageEnum(MessageEnum.PHONE_EXISTS);
            }
        }
        if (user.getIdCard() != null && idCardExists(user.getIdCard()))
            return resultData.setMessageEnum(MessageEnum.IDCARD_EXISTS);

        if (user.getPassword() != null)
            user.setPassword(PasswordUtil.INSTANCE.encode(user.getPassword().getBytes()));
        if (user.getPayPassword() != null)
            user.setPayPassword(PasswordUtil.INSTANCE.encode(user.getPayPassword().getBytes()));
        user.setRegTime(new Date());
        user.setUserState(true);
        user.setInviteCode(RandomStringUtils.randomAlphanumeric(30));
        if (userDAO.insert(user) > 0) {
            //初始化用户账户
            userMoneyDAO.insert(new UserMoney(user.getId()));
            InnerMessage message = new InnerMessage();
            message.setUserId(user.getId());
            message.setPhone(user.getPhone());
            return resultData.setMessageEnum(MessageEnum.SUCCESS).
                    setData(new TriggerEventResult<InnerMessage>().setData(message).setUserId(user.getId()));
        }
        return resultData;
    }

    @Override
    @DataSource("read")
    public User userInfoById(Integer id) {
        return userDAO.userInfoById(id);
    }

    @Override
    @DataSource("read")
    public User userInfoByPhone(String phone, UserEnum state) {
        if (!CommonUtil.INSTANCE.isPhone(phone))
            return null;
        User where = new User();
        where.setPhone(phone);
        if (state != null)
            where.setUserState(state.getState() == 1);
        List<User> userList = userDAO.selectSelective(where);
        if (userList.size() == 1)
            return userList.get(0);
        return null;
    }

    @Override
    @DataSource("read")
    public User userInfoByInviteCodeAndId(Integer userId, String inviteCode) {
        return userDAO.userInfoByInviteCodeAndId(userId, inviteCode);
    }

    @Override
    @DataSource("read")
    public User loginByPhoneAndPassword(String phone, String password) {
        User where = new User();
        where.setPhone(phone);
        where.setPassword(PasswordUtil.INSTANCE.encode(password.getBytes()));
        return userDAO.userInfoByPhoneAndPassword(where);
    }

    @Override
    @DataSource("write")
    public ResultData<User> saveUserInfo(Integer id, User user) {
        ResultData<User> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (userDAO.selectById(id) == null)
            return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);
        user.setId(id);
        if (user.getPassword() != null && !StringUtils.isEmpty(user.getPassword()))
            user.setPassword(PasswordUtil.INSTANCE.encode(user.getPassword().getBytes()));

        if (user.getPayPassword() != null && !StringUtils.isEmpty(user.getPayPassword()))
            user.setPayPassword(PasswordUtil.INSTANCE.encode(user.getPayPassword().getBytes()));

        if (user.getInviteName() != null && !StringUtils.isBlank(user.getInviteName())) {
            User invite = new User();
            invite.setPhone(user.getInviteName());
            List<User> inviteList = userDAO.selectSelective(invite);
            Integer inviteUid;
            if (inviteList.size() == 1 && !(inviteUid = inviteList.get(0).getId()).equals(id))
                user.setInviteUid(inviteUid);
            else
                return resultData.setMessageEnum(MessageEnum.INVITE_USER_NOT_EXISTS);
        }
        if (user.getIdCard() != null && idCardExists(user.getIdCard()))
            return resultData.setMessageEnum(MessageEnum.IDCARD_EXISTS);

        try {
            if (userDAO.updateById(user) > 0) {
                User updatedUser = userDAO.selectById(id);
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(updatedUser);
            }
        } catch (Exception e) {
            return resultData;
        }

        return resultData;
    }

    @Override
    @Transactional
    @DataSource("write")
    public ResultData<Integer> saveBankCard(UserBank bank) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        if (userDAO.selectById(bank.getUserId()) == null)
            return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        bank.setState(UserEnum.BANK_IN_USED.getState());
        bank.setAddTime(new Date());
        if (null == bank.getId()) {
            UserBank where = new UserBank();
            where.setBankNumber(bank.getBankNumber());
            where.setUserId(bank.getUserId());
            List<UserBank> userBanks = userBankDAO.selectSelective(where);
            if (null != userBanks && userBanks.size() > 0) {
                return resultData.setMessageEnum(MessageEnum.USER_BANK_CARD_EXISTS);
            }

            if (userBankDAO.insert(bank) > 0) {
                User update = new User();
                update.setId(bank.getUserId());
                update.setIsBindCard(true);
                if (userDAO.updateById(update) > 0)
                    return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(bank.getId());
            }
        } else {
            //更新银行卡
            if (userBankDAO.updateById(bank) > 0) {
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(bank.getId());
            }
        }

        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> deleteCard(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        UserBank bankInfo = userBankDAO.selectById(id);
        if (null == bankInfo)
            return resultData.setMessageEnum(MessageEnum.USER_BANK_CARD_NOT_EXISTS);

        if (userBankDAO.deleteById(id) > 0) {
            UserBank where = new UserBank();
            where.setUserId(bankInfo.getUserId());
            if (userBankDAO.selectSelective(where).size() == 0) {
                User update = new User();
                update.setId(bankInfo.getUserId());
                update.setIsBindCard(false);
                userDAO.updateById(update);
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DataSource("read")
    public List<UserBank> bankCardList(Integer userId) {
        UserBank where = new UserBank();
        where.setUserId(userId);
        where.setState(UserEnum.BANK_IN_USED.getState());
        return userBankDAO.selectSelective(where);
    }

    @Override
    @DataSource("read")
    public UserBank bankCard(Integer userId, Integer id) {
        return userBankDAO.bankInfoByUserIdAndId(userId, id);
    }

    @Override
    @DataSource("read")
    public UserBank bankCardByOrderNumber(String orderNumber) {
        UserBank where = new UserBank();
        where.setOrderNumber(orderNumber);
        List<UserBank> banks = userBankDAO.selectSelective(where);
        if (null == banks || banks.size() != 1)
            return null;
        return banks.get(0);
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> addAddress(UserAddress address) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        // 用户不存在
        if (userDAO.selectById(address.getUserId()) == null)
            return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        UserAddress where = new UserAddress();
        where.setUserId(address.getUserId());
        List<UserAddress> addressList = addressDAO.selectSelective(where);
        // 地址数已达最大数
        if (addressList != null && addressList.size() >= 5)
            return resultData.setMessageEnum(MessageEnum.USER_ADDRESS_FULL);

        // 校验手机号
        if (!CommonUtil.INSTANCE.isPhone(address.getReceivePhone()))
            return resultData.setMessageEnum(MessageEnum.PHONE_ERROR);

        // 新增地址
        address.setAddTime(new Date());
        if (addressDAO.insert(address) > 0) {
            // 首个地址设置为默认地址
            if (addressList == null || addressList.isEmpty()) {
                // 设置了为默认地址，则其他均不能为默认地址
                addressDAO.setDefault(address.getId(), address.getUserId());
            } else {
                // 是否设置为默认地址
                if (address.getDefault()) {
                    addressDAO.setNoDefault(address.getId(), address.getUserId());
                }
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(address.getId());
        }
        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> updateAddress(Integer id, UserAddress address) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        // 地址不存在
        if (addressDAO.selectByIdAndUserId(id, address.getUserId(), false) == null)
            return resultData.setMessageEnum(MessageEnum.USER_ADDRESS_NO_EXISTS);

        // 手机号校验
        if (address.getReceivePhone() != null && !CommonUtil.INSTANCE.isPhone(address.getReceivePhone()))
            return resultData.setMessageEnum(MessageEnum.PHONE_ERROR);

        // 更新地址
        address.setId(id);
        if (addressDAO.updateById(address) > 0) {
            UserAddress where = new UserAddress();
            where.setUserId(address.getUserId());
            List<UserAddress> addressList = addressDAO.selectSelective(where);
            // 首个地址必须设置为默认地址
            if (addressList.size() == 1 || addressDAO.getDefaultNum(address.getUserId()) == 0) {
                addressDAO.setDefault(id, address.getUserId());
            } else {
                if (null != address.getDefault() && address.getDefault()) {
                    addressDAO.setNoDefault(id, address.getUserId());
                }
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(address.getId());
        }
        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> deleteAddress(UserAddress address) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        // 地址不存在
        if (addressDAO.selectByIdAndUserId(address.getId(), address.getUserId(), false) == null) {
            return resultData.setMessageEnum(MessageEnum.USER_ADDRESS_NO_EXISTS);
        }

        // 删除地址
        if (addressDAO.deleteById(address.getId()) > 0) {
            // 删除后没有默认地址，则将最近新增的地址设置为默认地址
            if (addressDAO.getDefaultNum(address.getUserId()) == 0) {
                addressDAO.setDefaultByTime(address.getUserId());
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    @DataSource("read")
    public List<UserAddress> addressList(Integer userId) {
        UserAddress where = new UserAddress();
        where.setUserId(userId);
        return addressDAO.selectSelective(where);
    }


    @Override
    @DataSource("read")
    public UserSummaryInfo userSummaryInfo(User user) {
        UserSummaryInfo summaryInfo = new UserSummaryInfo();
        Integer sumSeedCount = seedOrderDAO.sumSeedCountByUserId(user.getId());
        if (null != sumSeedCount) {
            summaryInfo.setSeedCount(sumSeedCount);
        } else {
            summaryInfo.setSeedCount(0);
        }
        summaryInfo.setAvailableAmount(userMoneyDAO.selectById(user.getId()).getAvailableMoney());
        Integer blockCount = reapDAO.countBlockIdByUserId(user.getId());
        if (null == blockCount)
            summaryInfo.setBlockCount(0);
        else
            summaryInfo.setBlockCount(blockCount);
        summaryInfo.setPhone(user.getPhone());
        Integer count = userMessageDAO.countMessageByUserId(user.getId());
        if (count > 0)
            summaryInfo.setHasNewMessage(true);
        else
            summaryInfo.setHasNewMessage(false);

        if (null == user.getPayPassword() || StringUtils.isBlank(user.getPayPassword())) {
            summaryInfo.setHasPayPassword(false);
        } else
            summaryInfo.setHasPayPassword(true);

        return summaryInfo;
    }


    private boolean idCardExists(String idCard) {
        User where = new User();
        where.setIdCard(idCard);
        return userDAO.selectSelective(where).size() > 0;
    }


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<UserMessage> userMessageList(Integer page, Integer size, Integer userId) {
        userMessageDAO.messageListByUserId(userId, false);
        return null;
    }

    @Override
    @DataSource("read")
    public UserAddress userDefaultAddress(Integer userId) {
        return addressDAO.userDefaultAddress(userId);
    }

    @Override
    @DataSource("read")
    public UserAddress userAddressByIdAndUserId(Integer id, Integer userId, Boolean isDelete) {
        return addressDAO.selectByIdAndUserId(id, userId, isDelete);
    }

    @Override
    @TriggerEvent(action = "login")
    @DataSource("write")
    public ResultData<TriggerEventResult<LoginResponse>> loginSuccess(Integer id, String phone) {
        ResultData<TriggerEventResult<LoginResponse>> resultData = new ResultData<>(MessageEnum.ERROR, new TriggerEventResult<>());
        LoginResponse response = new LoginResponse();
        Map<String, String> map = new HashMap<>();
        map.put(ApiEnum.LOGIN_USER_ID.getTypeStr(), id.toString());
        map.put(ApiEnum.LOGIN_PHONE.getTypeStr(), phone);
        map.put(ApiEnum.LOGIN_TIME.getTypeStr(), Long.toString(System.currentTimeMillis()));
        response.setToken(TokenUtil.INSTANCE.addToken(map));
        return resultData.setMessageEnum(MessageEnum.SUCCESS).
                setData(resultData.getData().setData(response).setUserId(id));
    }


    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<User> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("用户列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadUtil<>(userDAO, search, User.class, downloadInfo)).start();
    }

    @Override
    @DataSource("read")
    public User userInfo(User where) {
        return userDAO.selectSelective(where).stream().filter(Objects::nonNull).findFirst().orElse(null);
    }
}

