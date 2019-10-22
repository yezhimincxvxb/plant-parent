package com.moguying.plant.utils.message;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.utils.CommonUtil;
import com.moguying.plant.utils.CurlUtil;
import com.moguying.plant.utils.message.request.SmsSendRequest;
import com.moguying.plant.utils.message.response.SmsSendResponse;


public enum  PhoneMessageUtil {
    INSTANCE;

    public ResultData<Integer> send(String sendUrl, String account, String password, String phone, String msg){
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(!CommonUtil.INSTANCE.isPhone(phone))
            return resultData.setMessageEnum(MessageEnum.PHONE_ERROR);
        SmsSendRequest smsSendRequest = new SmsSendRequest(account,password,msg,phone);
        String requestStr = JSON.toJSONString(smsSendRequest);
        String responseStr = CurlUtil.INSTANCE.jsonPostRequest(sendUrl,requestStr,"POST");
        SmsSendResponse response  = JSON.parseObject(responseStr, SmsSendResponse.class);
        if(null != response) {
            if ("0".equals(response.getCode()))
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }
}
