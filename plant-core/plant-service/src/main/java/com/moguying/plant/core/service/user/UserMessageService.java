package com.moguying.plant.core.service.user;

import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.UserMessage;

public interface UserMessageService {

    
    Integer addMessage(InnerMessage message, String actionCode);

    
    Boolean setMessageState(UserMessage userMessage);
}
