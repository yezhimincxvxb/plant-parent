package com.moguying.plant.core.service.common.message;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.service.common.message.request.SmsSendRequest;
import com.moguying.plant.core.service.common.message.response.SmsSendResponse;
import com.moguying.plant.utils.CommonUtil;
import com.moguying.plant.utils.CurlUtil;


public enum MessageSendService {
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
