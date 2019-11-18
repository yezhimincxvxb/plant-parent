package com.moguying.plant.core.service.system;

import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.system.vo.InnerMessage;

public interface PhoneMessageService {


    ResultData<Integer> sendCodeMessage(SendMessage seedMessage);

    ResultData<Integer> sendOtherMessage(InnerMessage message, Integer typeId);

    ResultData<Integer> sendSaleMessage(String phone);


    PhoneMessage messageByPhone(String phone);


    Integer setMessageState(Integer id, SystemEnum state);


    Integer validateMessage(String phone, String code);

}
