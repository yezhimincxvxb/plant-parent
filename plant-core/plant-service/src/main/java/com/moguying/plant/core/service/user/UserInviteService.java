package com.moguying.plant.core.service.user;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.InnerMessage;
import com.moguying.plant.core.entity.dto.Reap;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.dto.UserInvite;
import com.moguying.plant.core.entity.vo.InviteStatistics;

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
