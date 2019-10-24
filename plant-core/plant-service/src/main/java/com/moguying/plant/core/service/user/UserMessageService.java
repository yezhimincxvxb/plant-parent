package com.moguying.plant.core.service.user;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.system.InnerMessage;
import com.moguying.plant.core.entity.user.UserMessage;

public interface UserMessageService {

    @DataSource("write")
    Integer addMessage(InnerMessage message, String actionCode);

    @DataSource("write")
    Boolean setMessageState(UserMessage userMessage);
}
