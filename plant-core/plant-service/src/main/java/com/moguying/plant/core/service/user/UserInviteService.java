package com.moguying.plant.core.service.user;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserInvite;
import com.moguying.plant.core.entity.user.vo.InviteStatistics;

public interface UserInviteService {
    @DataSource("write")
    ResultData<TriggerEventResult<InnerMessage>> inviterPlanted(User user, Reap reap, User inviteUser);

    @DataSource("write")
    ResultData<User> saveInviteInfo(UserInvite userInvite);

    @DataSource("read")
    InviteStatistics inviteStatistics(Integer userId);

    @DataSource("read")
    PageResult<UserInvite> inviteList(Integer page, Integer size, Integer userId);
}
