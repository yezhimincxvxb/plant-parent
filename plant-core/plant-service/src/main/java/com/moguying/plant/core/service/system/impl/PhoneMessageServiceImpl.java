package com.moguying.plant.core.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.config.RabbitConfig;
import com.moguying.plant.core.dao.system.PhoneMessageDAO;
import com.moguying.plant.core.dao.system.PhoneMessageTplDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.system.PhoneMessageTpl;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.service.common.message.MessageSendService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.mq.sender.PhoneMessageSender;
import com.moguying.plant.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class PhoneMessageServiceImpl implements PhoneMessageService {

    @Value("${message.time}")
    private String time;

    @Value("${message.send.url}")
    private String sendUrl;

    @Value("${message.account}")
    private String account;

    @Value("${message.sale.account}")
    private String saleAccount;

    @Value("${message.password}")
    private String password;


    @Autowired
    private PhoneMessageDAO phoneMessageDAO;

    @Autowired
    private PhoneMessageTplDAO tplDAO;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送验证码短信
     * 在规定时间内不可重复发送
     * @param sendMessage
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Boolean> sendCodeMessage(SendMessage sendMessage) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        String code = CommonUtil.INSTANCE.messageCode();
        long inTime = (Long.parseLong(time) / 1000) / 60;
        PhoneMessageTpl codeContent = tplDAO.selectOne(new QueryWrapper<PhoneMessageTpl>().lambda().eq(PhoneMessageTpl::getCode,"code"));
        if(messageByPhone(sendMessage.getPhone()) != null){
            MessageEnum messageEnum = MessageEnum.CAN_NOT_REPEAT_SEND_MESSAGE;
            messageEnum.setMessage(messageEnum.getMessage().replace("time",String.valueOf(inTime)));
            return resultData.setMessageEnum(messageEnum);
        }
        if(send(sendMessage.getPhone(),codeContent.getContent(),code,code))
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        return resultData;
    }


    @Override
    public boolean send(String phone, String template, String code,String... params) {
        for(int i = 1; i <= params.length ; i++) {
            template = template.replace("{param" + i + "}", params[i - 1]);
        }
        PhoneMessage add = new PhoneMessage();
        if (null != code) add.setCode(code);
        add.setMessage(template);
        add.setPhone(phone);
        add.setAddTime(new Date());
        if(phoneMessageDAO.insert(add) > 0) {
            amqpTemplate.convertAndSend(RabbitConfig.PHONE_MESSAGE, JSON.toJSONString(new PhoneMessageSender(phone, template)));
            return true;
        }
        return false;
    }

    @Override
    @DS("read")
    public PhoneMessage messageByPhone(String phone) {
        // 1分钟内有效
        Long inTime = (System.currentTimeMillis() - Long.parseLong(time)) / 1000;
        return phoneMessageDAO.selectByPhoneInTime(phone,String.valueOf(inTime));
    }

    @Override
    @DS("write")
    public Integer setMessageState(Integer id, SystemEnum state) {
        PhoneMessage update = new PhoneMessage();
        update.setId(id);
        update.setState(state.getState());
        return phoneMessageDAO.updateById(update);
    }

    @Override
    @DS("write")
    public Integer validateMessage(String phone, String code) {
        PhoneMessage message = messageByPhone(phone);
        if(message == null)
            return -1;
        setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);
        return message.getCode().equals(code) ? 1 : -1;
    }
}
