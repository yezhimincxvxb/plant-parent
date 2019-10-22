package com.moguying.plant.core.aop;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.entity.dto.payment.PaymentInfo;
import com.moguying.plant.core.entity.dto.payment.callback.CallBackResponse;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.service.payment.PaymentInfoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CallBackBefore {

    Logger log = LoggerFactory.getLogger(CallBackBefore.class);

    @Autowired
    PaymentInfoService paymentInfoService;

    @Around("execution(* com.moguying.plant.payment.controller.CallBackController.*Callback(..)) && args(request,..)")
    private Object setCallBackData(ProceedingJoinPoint pjp , HttpServletRequest request) throws Throwable {
        PaymentResponse<CallBackResponse> paymentResponse = new PaymentResponse<>();
        paymentResponse.setCode(request.getParameter("code"));
        paymentResponse.setMsg(request.getParameter("msg"));
        paymentResponse.setResponseType(request.getParameter("responseType"));
        paymentResponse.setResponseParameters(request.getParameter("responseParameters"));
        paymentResponse.setSign(request.getParameter("sign"));
        paymentResponse.setData(JSON.parseObject(paymentResponse.getResponseParameters(), CallBackResponse.class));
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNumber(paymentResponse.getData().getMerMerOrderNo());
        paymentInfo.setNotifyResponse(JSON.toJSONString(paymentResponse));
        paymentInfoService.updateNotifyResponse(paymentInfo);
        return pjp.proceed(new Object[]{request,paymentResponse});

    }

}
