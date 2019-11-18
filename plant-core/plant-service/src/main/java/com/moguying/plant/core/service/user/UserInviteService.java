package com.moguying.plant.core.service.user;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserInvite;
import com.moguying.plant.core.entity.user.vo.InviteStatistics;

public interface UserInviteService {

    ResultData<TriggerEventResult<InnerMessage>> inviterPlanted(User user, Reap reap, User inviteUser);


    ResultData<User> saveInviteInfo(UserInvite userInvite);


    InviteStatistics inviteStatistics(Integer userId);


    PageResult<UserInvite> inviteList(Integer page, Integer size, Integer userId);
}
