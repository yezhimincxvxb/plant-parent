package com.moguying.plant.core.service.system;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.constant.SystemEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.SendMessage;
import com.moguying.plant.core.entity.dto.InnerMessage;
import com.moguying.plant.core.entity.dto.PhoneMessage;

public interface PhoneMessageService {

    @DataSource("write")
    ResultData<Integer> sendCodeMessage(SendMessage seedMessage);


    @DataSource("write")
    ResultData<Integer> sendOtherMessage(InnerMessage message, Integer typeId);

    @DataSource("write")
    ResultData<Integer> sendSaleMessage(String phone);

    @DataSource("read")
    PhoneMessage messageByPhone(String phone);

    @DataSource("write")
    Integer setMessageState(Integer id, SystemEnum state);


    @DataSource("write")
    Integer validateMessage(String phone, String code);

}
