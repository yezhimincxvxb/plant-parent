package com.moguying.plant.core.service.user.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.*;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.dao.account.UserMoneyDAO;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapWeighDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDAO;
import com.moguying.plant.core.dao.user.*;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.*;
import com.moguying.plant.core.entity.user.dto.UserPlantMoneyDto;
import com.moguying.plant.core.entity.user.vo.LoginResponse;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import com.moguying.plant.core.entity.user.vo.UserPlantMoneyVo;
import com.moguying.plant.core.entity.user.vo.UserSummaryInfo;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.seed.SeedOrderService;
import com.moguying.plant.core.service.user.UserService;
import com.moguying.plant.utils.CommonUtil;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.PasswordUtil;
import com.moguying.plant.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    private ReapWeighDAO reapWeighDAO;

    @Autowired
    private UserActivityLogDAO userActivityLogDAO;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private SeedOrderService seedOrderService;

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Value("${excel.download.dir}")
    private String downloadDir;


    @Override
    @DS("read")
    public PageResult<User> userList(Integer page, Integer size, User where) {
        IPage<User> pageResult = userDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @TriggerEvent(action = "register")
    @Override
    @DS("write")
    @Transactional
    public ResultData<TriggerEventResult<InnerMessage>> addUser(User user) {
        ResultData<TriggerEventResult<InnerMessage>> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 手机号是否已存在
        if (user.getPhone() != null) {
            User where = new User();
            where.setPhone(user.getPhone());
            if (userDAO.selectOne(new QueryWrapper<>(where)) != null) {
                return resultData.setMessageEnum(MessageEnum.PHONE_EXISTS);
            }
        }

        // 身份证是否存在
        if (user.getIdCard() != null && idCardExists(user.getIdCard()))
            return resultData.setMessageEnum(MessageEnum.IDCARD_EXISTS);

        // 密码
        if (user.getPassword() != null)
            user.setPassword(PasswordUtil.INSTANCE.encode(user.getPassword().getBytes()));

        // 支付密码
        if (user.getPayPassword() != null)
            user.setPayPassword(PasswordUtil.INSTANCE.encode(user.getPayPassword().getBytes()));

        user.setRegTime(new Date());
        user.setUserState(true);

        // 新增用户
        if (userDAO.insert(user) > 0) {
            // 更新邀请码
            User update = new User();
            update.setId(user.getId());
            update.setInviteCode(CommonUtil.INSTANCE.createInviteCode(user.getId()));
            userDAO.updateById(update);

            // 初始化用户账户
            userMoneyDAO.insert(new UserMoney(user.getId()));

            // 初始化用户产量信息
            reapWeighDAO.insert(new ReapWeigh(user.getId()));

            InnerMessage message = new InnerMessage();
            message.setUserId(user.getId());
            message.setPhone(user.getPhone());


            return resultData.setMessageEnum(MessageEnum.SUCCESS).
                    setData(new TriggerEventResult<InnerMessage>().setData(message).setUserId(user.getId()));
        }
        return resultData;
    }

    @Override
    @DS("read")
    public User userInfoById(Integer id) {
        return userDAO.userInfoById(id);
    }

    @Override
    @DS("read")
    public User userInfoByPhone(String phone, UserEnum state) {
        if (!CommonUtil.INSTANCE.isPhone(phone))
            return null;
        User where = new User();
        where.setPhone(phone);
        if (state != null)
            where.setUserState(state.getState() == 1);
        return userDAO.selectOne(new QueryWrapper<>(where));
    }

    @Override
    @DS("read")
    public User userInfoByInviteCode(String inviteCode, UserEnum state) {
        User where = new User();
        where.setInviteCode(inviteCode);

        if (state != null) where.setUserState(state.getState() == 1);
        return userDAO.selectOne(new QueryWrapper<>(where));
    }

    @Override
    @DS("read")
    public User userInfoByInviteCodeAndId(Integer userId, String inviteCode) {
        return userDAO.userInfoByInviteCodeAndId(userId, inviteCode);
    }

    @Override
    @DS("read")
    public User loginByPhoneAndPassword(String phone, String password) {
        User where = new User();
        where.setPhone(phone);
        where.setPassword(PasswordUtil.INSTANCE.encode(password.getBytes()));
        return userDAO.userInfoByPhoneAndPassword(where);
    }

    @Override
    @DS("write")
    @Transactional
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
            User inviteUser = userDAO.selectOne(new QueryWrapper<>(invite));
            if (null != inviteUser)
                user.setInviteUid(inviteUser.getId());
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
    @DS("write")
    @Transactional
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
    @DS("write")
    @Transactional
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
    @DS("read")
    public List<UserBank> bankCardList(Integer userId) {
        UserBank where = new UserBank();
        where.setUserId(userId);
        where.setState(UserEnum.BANK_IN_USED.getState());
        return userBankDAO.selectSelective(where);
    }

    @Override
    @DS("read")
    public UserBank bankCard(Integer userId, Integer id) {
        return userBankDAO.bankInfoByUserIdAndId(userId, id);
    }

    @Override
    @DS("read")
    public UserBank bankCardByOrderNumber(String orderNumber) {
        UserBank where = new UserBank();
        where.setOrderNumber(orderNumber);
        List<UserBank> banks = userBankDAO.selectSelective(where);
        if (null == banks || banks.size() != 1)
            return null;
        return banks.get(0);
    }

    @Override
    @DS("write")
    @Transactional
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
                if (address.getIsDefault()) {
                    addressDAO.setNoDefault(address.getId(), address.getUserId());
                }
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(address.getId());
        }
        return resultData;
    }

    @Override
    @DS("write")
    @Transactional
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
                if (null != address.getIsDefault() && address.getIsDefault()) {
                    addressDAO.setNoDefault(id, address.getUserId());
                }
            }
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(address.getId());
        }
        return resultData;
    }

    @Override
    @DS("write")
    @Transactional
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
    @DS("read")
    public List<UserAddress> addressList(Integer userId) {
        UserAddress where = new UserAddress();
        where.setUserId(userId);
        return addressDAO.selectSelective(where);
    }


    @Override
    @DS("read")
    public UserSummaryInfo userSummaryInfo(User user) {

        UserSummaryInfo summaryInfo = new UserSummaryInfo();
        summaryInfo.setPhone(user.getPhone());
        summaryInfo.setAvailableAmount(userMoneyDAO.selectById(user.getId()).getAvailableMoney());

        // 持有可种植的菌包份数
        Integer sumSeedCount = seedOrderDAO.sumSeedCountByUserId(user.getId());
        if (null != sumSeedCount) {
            summaryInfo.setSeedCount(sumSeedCount);
        } else {
            summaryInfo.setSeedCount(0);
        }

        // 用户有种植的大棚个数
        Integer blockCount = reapDAO.countBlockIdByUserId(user.getId());
        if (null == blockCount) {
            summaryInfo.setBlockCount(0);
        } else {
            summaryInfo.setBlockCount(blockCount);
        }

        // 是否有新消息
        Integer count = userMessageDAO.countMessageByUserId(user.getId());
        if (count > 0) {
            summaryInfo.setHasNewMessage(true);
        } else {
            summaryInfo.setHasNewMessage(false);
        }

        // 是否设置了支付密码
        if (null == user.getPayPassword() || StringUtils.isBlank(user.getPayPassword())) {
            summaryInfo.setHasPayPassword(false);
        } else {
            summaryInfo.setHasPayPassword(true);
        }

        return summaryInfo;
    }


    private boolean idCardExists(String idCard) {
        User where = new User();
        where.setIdCard(idCard);
        return userDAO.selectCount(new QueryWrapper<>(where)) > 0;
    }


    @Override
    @DS("read")
    public PageResult<UserMessage> userMessageList(Integer page, Integer size, Integer userId) {
        IPage<UserMessage> pageResult = userMessageDAO.messageListByUserId(new Page<>(page, size), userId, false);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public UserAddress userDefaultAddress(Integer userId) {
        return addressDAO.userDefaultAddress(userId);
    }

    @Override
    @DS("read")
    public UserAddress userAddressByIdAndUserId(Integer id, Integer userId, Boolean isDelete) {
        return addressDAO.selectByIdAndUserId(id, userId, isDelete);
    }

    @Override
    @TriggerEvent(action = "login")
    @DS("write")
    @Transactional
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
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<User> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("用户列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(userDAO, search, User.class, downloadInfo)).start();
    }

    @Override
    @DS("read")
    public User userInfo(User where) {
        return userDAO.selectOne(new QueryWrapper<>(where));
    }

    @Override
    @DS("write")
    @Transactional
    public List<UserActivityLogVo> inviteUser(Integer userId) {

        // 活动开启时间
        Date startTime = DateUtil.INSTANCE.stringToDate(ActivityEnum.START_ACTIVITY.getMessage());

        // 邀请记录
        QueryWrapper<UserActivityLog> wrapper = new QueryWrapper<UserActivityLog>()
                .eq("user_id", userId)
                .likeRight("number", OrderPrefixEnum.INVITE_REWARD.getPreFix())
                .orderByAsc("add_time");
        List<UserActivityLog> userActivityLogs = userActivityLogDAO.selectList(wrapper);

        // 发奖
        List<User> users = userDAO.inviteUser(startTime, userId);
        if (Objects.isNull(users) || users.size() == 0) return null;
        // 前三个邀请成功才有奖励
        if (users.size() <= 3) {
            for (int i = userActivityLogs.size(); i < users.size(); i++) {
                User user = users.get(i);
                UserActivityLog userActivityLog = new UserActivityLog();
                userActivityLog.setUserId(userId);
                userActivityLog.setNumber(OrderPrefixEnum.INVITE_REWARD.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
                userActivityLog.setFriendId(user.getId());
                Fertilizer fertilizer;
                switch (i) {
                    case 0:
                        // 一份12.5的菌包
                        userActivityLog.setSeedTypeId(ActivityEnum.FREE_SEED_30DAY.getState());
                        userActivityLog.setName(ActivityEnum.FREE_SEED_30DAY.getMessage());
                        break;
                    case 1:
                        // 5折酒水满减券
                        fertilizer = getFertilizer(FieldEnum.WINE_FERTILIZER.getField());
                        if (Objects.isNull(fertilizer)) return null;
                        userActivityLog.setFertilizerId(fertilizer.getId());
                        userActivityLog.setName(ActivityEnum.WINE_FERTILIZER.getMessage());
                        break;
                    case 2:
                        // 商城食品满减券
                        fertilizer = getFertilizer(FieldEnum.MALL_FOOL_FERTILIZER.getField());
                        if (Objects.isNull(fertilizer)) return null;
                        userActivityLog.setFertilizerId(fertilizer.getId());
                        userActivityLog.setName(ActivityEnum.MALL_FOOL_FERTILIZER.getMessage());
                        break;
                    default:
                        break;
                }
                userActivityLog.setState(false);
                userActivityLog.setAddTime(user.getRegTime());
                if (userActivityLogDAO.insert(userActivityLog) <= 0) return null;
                userActivityLogs.add(userActivityLog);
            }
        }

        // 赋值属性
        List<UserActivityLogVo> userActivityLogVos = Collections.synchronizedList(new ArrayList<>());
        userActivityLogs.forEach(userActivityLog -> {
            UserActivityLogVo userActivityLogVo = new UserActivityLogVo();
            BeanUtils.copyProperties(userActivityLog, userActivityLogVo);
            userActivityLogVos.add(userActivityLogVo);
        });

        return userActivityLogVos;
    }

    /**
     * 根据触发事件获取对应的券
     */
    private Fertilizer getFertilizer(String event) {
        QueryWrapper<Fertilizer> queryWrapper = new QueryWrapper<Fertilizer>().eq("trigger_get_event", event);
        List<Fertilizer> fertilizers = fertilizerDAO.selectList(queryWrapper);
        if (Objects.isNull(fertilizers) || fertilizers.size() != 1) return null;
        return fertilizers.get(0);
    }

    @Override
    @DS("write")
    @Transactional
    public ResultData<Integer> pickUpReward(Integer userId, Integer id) {

        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);

        // 已领取
        UserActivityLog userActivityLog = userActivityLogDAO.selectById(id);
        if (Objects.isNull(userActivityLog) || userActivityLog.getState())
            return resultData.setMessageEnum(MessageEnum.INVITE_REWARD);

        // 领取
        if (Objects.equals(userActivityLog.getName(), ActivityEnum.FREE_SEED_30DAY.getMessage())) {
            // 一份12.5的菌包
            Seed seed = seedOrderService.getSeedType(userId);
            if (Objects.isNull(seed)) return resultData;

        } else if (Objects.equals(userActivityLog.getName(), ActivityEnum.WINE_FERTILIZER.getMessage())) {
            // 5折酒水满减券
            resultData = fertilizerService.distributeFertilizer(FieldEnum.WINE_FERTILIZER.getField(),
                    new TriggerEventResult().setUserId(userId), userActivityLog.getFertilizerId());
            if (resultData.getMessageEnum().equals(MessageEnum.ERROR)) return resultData;

        } else if (Objects.equals(userActivityLog.getName(), ActivityEnum.MALL_FOOL_FERTILIZER.getMessage())) {
            // 商城食物满减券
            resultData = fertilizerService.distributeFertilizer(FieldEnum.MALL_FOOL_FERTILIZER.getField(),
                    new TriggerEventResult().setUserId(userId), userActivityLog.getFertilizerId());
            if (resultData.getMessageEnum().equals(MessageEnum.ERROR)) return resultData;

        }

        // 更新状态
        userActivityLog.setState(true).setReceiveTime(new Date());
        if (userActivityLogDAO.updateById(userActivityLog) <= 0)
            return resultData;

        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }

    @Override
    @DS("read")
    public Integer regUserTotal() {
        return userDAO.selectCount(new QueryWrapper<User>().lambda().eq(User::getUserState, UserEnum.BANK_IN_USED));
    }


    @Override
    @DS("read")
    public PageResult<UserPlantMoneyVo> userPlantMoneyList(Integer page, Integer size, UserPlantMoneyDto userPlantMoneyDto) {
        IPage<UserPlantMoneyVo> iPage = userDAO.userPlantMoneyList(new Page<>(page, size), userPlantMoneyDto);
        return new PageResult<>(iPage.getTotal(), iPage.getRecords());
    }
}

