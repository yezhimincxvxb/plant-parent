package com.moguying.plant.utils.message;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.utils.CurlUtil;
import com.moguying.plant.utils.message.request.SmsSendRequest;
import com.moguying.plant.utils.message.response.SmsSendResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MessageSendUtil {

    @Value("${message.send.url}")
    private String sendUrl;

    @Value("${message.account}")
    private String account;

    @Value("${message.sale.account}")
    private String saleAccount;

    @Value("${message.password}")
    private String password;


    public boolean sendNormal(String phone,String content){
        return send(sendUrl,account,password,phone,content);
    }

    public boolean sendSale(String phone,String content){
        return send(sendUrl,saleAccount,password,phone,content);
    }

    private boolean send(String sendUrl, String account, String password, String phone, String msg) {
        SmsSendRequest smsSendRequest = new SmsSendRequest(account, password, msg, phone);
        String requestStr = JSON.toJSONString(smsSendRequest);
        String responseStr = CurlUtil.INSTANCE.jsonPostRequest(sendUrl, requestStr, "POST");
        SmsSendResponse response = JSON.parseObject(responseStr, SmsSendResponse.class);
        if (null != response) {
            if ("0".equals(response.getCode()))
                return true;
        }
        return true;
    }




}
