package com.moguying.plant.core.service.user.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.dao.user.UserMessageDAO;
import com.moguying.plant.core.dao.user.UserMessageTplDAO;
import com.moguying.plant.core.entity.system.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserMessage;
import com.moguying.plant.core.entity.user.UserMessageTpl;
import com.moguying.plant.core.service.user.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageDAO userMessageDAO;

    @Autowired
    private UserMessageTplDAO messageTplDAO;

    @Autowired
    private UserDAO userDao;

    @Override
    @DataSource("write")
    public Integer addMessage(InnerMessage message, String actionCode) {

            Integer userId =  message.getUserId();
            User userInfo = userDao.userInfoById(userId);
            if (null == userInfo)
                return 0;
            UserMessageTpl tpl = messageTplDAO.selectById(actionCode);
            if(null == tpl)
                return 0;
            String messageTpl = tpl.getMessageTpl();
            UserMessage addMessage = new UserMessage();
            addMessage.setUserId(userId);
            String messageContent = messageTpl.replace("{userName}",message.getPhone());

            if(messageContent.contains("{seedTypeName}"))
                messageContent = messageContent.replace("{seedTypeName}", message.getSeedTypeName());

            if(messageContent.contains("{amount}"))
                messageContent = messageContent.replace("{amount}", message.getAmount());

            if(messageContent.contains("{block}"))
                messageContent = messageContent.replace("{block}",message.getBlockNumber());

            if(messageContent.contains("{time}"))
                messageContent = messageContent.replace("{time}",message.getTime());

            if(messageContent.contains("{count}"))
                messageContent = messageContent.replace("{count}",message.getCount());

            addMessage.setMessage(messageContent);
            addMessage.setTitle(tpl.getMessageTitle());
            addMessage.setAddTime(new Date());
            if(userMessageDAO.insert(addMessage) > 0)
                return addMessage.getId();
            return 0;
    }


    @Override
    @DataSource("write")
    public Boolean setMessageState(UserMessage userMessage) {
        return userMessageDAO.updateMessageByUserIdSelective(userMessage) > 0;
    }








}
