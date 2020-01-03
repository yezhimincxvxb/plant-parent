package com.moguying.plant.core.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.dao.admin.AdminActionDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.admin.AdminAction;
import com.moguying.plant.core.service.admin.AdminActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if(adminActionDAO.delete(new QueryWrapper<>()) > 0) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
            handlerMethods.forEach((key, value) -> {
                Class<?> controller = value.getMethod().getDeclaringClass();
                if (controller.getSimpleName().startsWith("B")) {
                    AdminAction action = new AdminAction();
                    action.setActionController(value.getMethod().getDeclaringClass().getSimpleName());
                    action.setActionMethod(value.getMethod().getName());
                    ApiOperation declaredAnnotation = value.getMethod().getDeclaredAnnotation(ApiOperation.class);
                    NoLogin noLoginAnnotation = value.getMethod().getDeclaredAnnotation(NoLogin.class);
                    Api apiAnnotation = controller.getDeclaredAnnotation(Api.class);
                    if(null != declaredAnnotation && null == noLoginAnnotation) {
                        action.setMethodDesc(declaredAnnotation.value());
                        if(null != apiAnnotation)
                            action.setControllerDesc(Arrays.toString(apiAnnotation.tags()));
                        adminActionDAO.insert(action);
                    }
                }
            });
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        }
        return resultData;
    }


    @Override
    public ResultData<Map<String, List<AdminAction>>> actionTree() {
        List<AdminAction> adminActions = adminActionDAO.selectList(new QueryWrapper<>());
        Map<String, List<AdminAction>> collect = adminActions.stream()
                .collect(Collectors.groupingBy(AdminAction::getActionController));
        return new ResultData<>(MessageEnum.SUCCESS,collect);
    }
}
