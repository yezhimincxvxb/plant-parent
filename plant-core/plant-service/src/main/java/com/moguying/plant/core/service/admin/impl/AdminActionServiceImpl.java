package com.moguying.plant.core.service.admin.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.dao.admin.AdminActionDAO;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminAction;
import com.moguying.plant.core.service.admin.AdminActionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Service
public class AdminActionServiceImpl implements AdminActionService {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private AdminActionDAO adminActionDAO;

    @Override
    @Transactional
    public ResultData<Boolean> generaAction() {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        //先清空
        int delete = adminActionDAO.delete(new QueryWrapper<>());
        if(delete > 0) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
            handlerMethods.forEach((key, value) -> {
                Class<?> controllerName = value.getMethod().getDeclaringClass();
                if (controllerName.getSimpleName().startsWith("B")) {
                    AdminAction action = new AdminAction();
                    action.setActionController(value.getMethod().getDeclaringClass().getSimpleName());
                    action.setActionMethod(value.getMethod().getName());
                    ApiOperation declaredAnnotation = value.getMethod().getDeclaredAnnotation(ApiOperation.class);
                    NoLogin noLoginAnnotation = value.getMethod().getDeclaredAnnotation(NoLogin.class);
                    if(null != declaredAnnotation && null == noLoginAnnotation) {
                        action.setActionDesc(declaredAnnotation.value());
                        adminActionDAO.insert(action);
                    }
                }
            });
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        }
        return resultData;
    }
}
