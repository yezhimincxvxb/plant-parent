package com.moguying.plant.core.service.user.impl;

import com.moguying.plant.constant.FieldEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.FarmerTrigger;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.dao.user.UserInviteDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.vo.InviteStatistics;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.reap.ReapFeeService;
import com.moguying.plant.core.service.user.UserInviteService;
import com.moguying.plant.utils.InterestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserInviteServiceImpl implements UserInviteService {

    @Autowired
    private UserInviteDAO userInviteDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private ReapFeeService reapFeeService;


    @Value("${invite.rate}")
    private String inviteRate;

    @DataSource("write")
    @FarmerTrigger(action = "invitedUser")
    @Override
    //warning:这里返回的User为邀请人id,而不是初邀请人
    public ResultData<User> saveInviteInfo(UserInvite saveInfo) {
        ResultData<User> resultData = new ResultData<>(MessageEnum.ERROR,null);

        if(saveInfo.getUserId() == null )
            return resultData.setMessageEnum(MessageEnum.PARAMETER_ERROR);

        UserInvite userInvite = userInviteDAO.selectById(saveInfo.getUserId());
        if(userInvite == null){
            if(userInviteDAO.insert(saveInfo) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(new User(saveInfo.getInviteUserId()));
        } else {
            if(userInviteDAO.incUserInviteInfo(saveInfo) > 0)
                return resultData.setMessageEnum(MessageEnum.UPDATE_INVITE_INFO_SUCCESS);
        }

        return resultData;
    }

    @DataSource("read")
    @Override
    public InviteStatistics inviteStatistics(Integer inviteUserId) {
        InviteStatistics statistics = new InviteStatistics();
        statistics.setInviteCount(userInviteDAO.countInvite(inviteUserId));
        statistics.setInvitePlantAmount(userInviteDAO.sumInviteAmount(FieldEnum.PLANT_AMOUNT.getField(),inviteUserId));
        statistics.setInviteAward(userInviteDAO.sumInviteAmount(FieldEnum.INVITE_AWARD.getField(),inviteUserId));
        User userInfo = userDAO.userInfoById(inviteUserId);
        statistics.setPhone(userInfo.getPhone());
        return statistics;
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<UserInvite> inviteList(Integer page, Integer size , Integer userId) {
        userInviteDAO.inviteList(userId);
        return null;
    }

    @TriggerEvent(action = "invitedPlant")
    @Override
    @DataSource("write")
    public ResultData<TriggerEventResult<InnerMessage>> inviterPlanted(User plantedUser, Reap reap, User inviteUser) {
        ResultData<TriggerEventResult<InnerMessage>> resultData = new ResultData<>(MessageEnum.ERROR,null);

        // 是否是渠道商
        if(!inviteUser.getIsChannel()) {
            // 用于在发站内信时使用
            UserInvite invite = new UserInvite();
            invite.setUserId(plantedUser.getId());
            invite.setInviteAward(InterestUtil.INSTANCE.multiply(reap.getPreAmount(), new BigDecimal(inviteRate)));
            invite.setPlantAmount(reap.getPreAmount());
            invite.setPhone(plantedUser.getPhone());
            invite.setInviteUserId(plantedUser.getInviteUid());
            saveInviteInfo(invite);

            //发放奖励
            UserMoneyOperator awardOperator = new UserMoneyOperator();
            awardOperator.setOperationId(reap.getOrderNumber());
            awardOperator.setOpType(MoneyOpEnum.INVITE_AWARD);
            UserMoney award = new UserMoney(plantedUser.getInviteUid());
            award.setAvailableMoney(invite.getInviteAward());
            awardOperator.setUserMoney(award);
            if (userMoneyService.updateAccount(awardOperator) == null) {
                return resultData.setMessageEnum(MessageEnum.USER_MONEY_UPDATE_FAIL);
            }
        }

        if(inviteUser.getIsChannel()) {
            // 计算种植运营费用
            ReapFee reapFee = new ReapFee();
            reapFee.setInviteUid(plantedUser.getInviteUid());
            reapFee.setUserId(plantedUser.getId());
            reapFee.setReapId(reap.getId());
            reapFeeService.addReapFee(reapFee);
        }

        InnerMessage message = new InnerMessage();
        message.setPhone(plantedUser.getPhone());
        message.setUserId(plantedUser.getInviteUid());
        return resultData.setMessageEnum(MessageEnum.SUCCESS).
                setData(new TriggerEventResult<InnerMessage>().setData(message).setUserId(plantedUser.getInviteUid()));
    }
}
