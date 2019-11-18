package com.moguying.plant.core.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.dao.system.PhoneMessageDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.service.common.message.MessageSendService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PhoneMessageServiceImpl implements PhoneMessageService {
    Logger logger = LoggerFactory.getLogger(PhoneMessageService.class);

    @Value("${message.code}")
    private String codeContentTpl;


    @Value("${message.seed.reap}")
    private String seedReapContentTpl;

    @Value("${message.withdraw.success}")
    private String withdrawSuccessContentTpl;

    @Value("${message.withdraw.fail}")
    private String withdrawFailContentTpl;

    @Value("${message.mall.send}")
    private String mallSendContentTpl;

    @Value("${message.fertilizer.send}")
    private String fertilizerSendContentTpl;

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
    PhoneMessageDAO phoneMessageDAO;

    @Override
    @DS("write")
    public ResultData<Integer> sendCodeMessage(SendMessage sendMessage) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        String code = CommonUtil.INSTANCE.messageCode();
        long inTime = (Long.parseLong(time) / 1000) / 60;
        String codeContent = codeContentTpl.replace("code", code);
        if (messageByPhone(sendMessage.getPhone()) != null) {
            MessageEnum messageEnum = MessageEnum.CAN_NOT_REPEAT_SEND_MESSAGE;
            messageEnum.setMessage(messageEnum.getMessage().replace("time", String.valueOf(inTime)));
            return resultData.setMessageEnum(messageEnum);
        }
        Integer addId = -1;
        if ((addId = send(sendMessage.getPhone(), codeContent, code)) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(addId);
        return resultData;
    }


    private Integer send(String phone, String content, String code) {
        return send(phone, content, code, account);
    }


    @Override
    @DS("write")
    public ResultData<Integer> sendSaleMessage(String phone) {
        Integer result = send(phone, fertilizerSendContentTpl, null, saleAccount);
        if (result > 0)
            return new ResultData<>(MessageEnum.SUCCESS, result);
        return new ResultData<>(MessageEnum.ERROR, result);
    }

    private Integer send(String phone, String content, String code, String account) {
        try {
            ResultData<Integer> sendResult = MessageSendService.INSTANCE.send(sendUrl, account, password, phone, content);
            if (sendResult.getMessageEnum().equals(MessageEnum.SUCCESS)) {
                PhoneMessage add = new PhoneMessage();
                if (null != code) {
                    add.setCode(code);
                }
                add.setMessage(content);
                add.setPhone(phone);
                add.setAddTime(new Date());
                if (phoneMessageDAO.insert(add) > 0)
                    return add.getId();
                return -1;
            }
            return -1;
        } catch (Exception e) {
            logger.info("Message Send Error");
            return -1;
        }

    }


    @Override
    @DS("write")
    public ResultData<Integer> sendOtherMessage(InnerMessage message, Integer typeId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        String messageContent = "";
        switch (typeId) {
            case 3:
                messageContent = seedReapContentTpl.replace("phone", message.getPhone())
                        .replace("count", message.getCount());
                break;
            case 4:
                messageContent = withdrawSuccessContentTpl.replace("time", message.getTime());
                break;
            case 5:
                messageContent = withdrawFailContentTpl.replace("time", message.getTime());
                break;
            case 6:
                messageContent = mallSendContentTpl;
                break;

        }
        if (send(message.getPhone(), messageContent, null) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    @DS("read")
    public PhoneMessage messageByPhone(String phone) {
        //1分钟内有效
        Long inTime = (System.currentTimeMillis() - Long.parseLong(time)) / 1000;
        return phoneMessageDAO.selectByPhoneInTime(phone, String.valueOf(inTime));
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
        if (message == null)
            return -1;
        setMessageState(message.getId(), SystemEnum.PHONE_MESSAGE_VALIDATE);
        return message.getCode().equals(code) ? 1 : -1;
    }
}
