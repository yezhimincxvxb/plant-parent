package com.moguying.plant.core.service.system;

import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.system.vo.InnerMessage;

public interface PhoneMessageService {


    ResultData<Boolean> sendCodeMessage(SendMessage seedMessage);

    PhoneMessage messageByPhone(String phone);


    Integer setMessageState(Integer id, SystemEnum state);


    Integer validateMessage(String phone, String code);

    boolean send(String phone,String template,String code,String... params);




}
