package com.moguying.plant.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.entity.payment.callback.CallBackResponseToPayment;
import com.moguying.plant.utils.CFCARAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CallBackInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(CallBackInterceptor.class);


    private String cerfile = "sq_formal_sign.cer";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("code", request.getParameter("code"));
        requestMap.put("msg", request.getParameter("msg"));
        requestMap.put("responseType", request.getParameter("responseType"));
        requestMap.put("responseParameters", request.getParameter("responseParameters"));
        String beforeSign = CFCARAUtil.joinMapValue(requestMap, '&');
        if (CFCARAUtil.verifyMessageByP1(beforeSign, request.getParameter("sign"),
                new ClassPathResource(cerfile).getPath())) {
            log.debug("验签成功");
            return true;
        } else {
            String responseStr = JSON.toJSONString(new CallBackResponseToPayment("000000", "success"));
            response.getWriter().write(responseStr);
            log.debug("验签失败");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
