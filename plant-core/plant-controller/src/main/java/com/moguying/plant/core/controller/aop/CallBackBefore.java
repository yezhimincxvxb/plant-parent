package com.moguying.plant.core.controller.aop;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import com.moguying.plant.core.entity.payment.callback.CallBackResponse;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.service.payment.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class CallBackBefore {


    @Autowired
    PaymentInfoService paymentInfoService;

    @Around("execution(* com.moguying.plant.core.controller.payment.CallBackController.*Callback(..)) && args(request,..)")
    private Object setCallBackData(ProceedingJoinPoint pjp, HttpServletRequest request) throws Throwable {
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
        return pjp.proceed(new Object[]{request, paymentResponse});

    }

}
