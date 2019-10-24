package com.moguying.plant.core.aop;


import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.utils.HandleDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(1)
public class DataSourceAop {

    Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("@annotation(com.moguying.plant.core.annotation.DataSource)))")
    private void dataSourceRoute(){
    }


    @Before("dataSourceRoute()")
    private void chooseDataSource(JoinPoint joinpoint){
        Object target = joinpoint.getTarget();
        String method = joinpoint.getSignature().getName();

        Class<?>[] classArray = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature)joinpoint.getSignature()).getMethod().getParameterTypes();

        try{
            Method m = classArray[0].getMethod(method,parameterTypes);
            if(null != m && m.isAnnotationPresent(DataSource.class)){
                DataSource dataSource = m.getAnnotation(DataSource.class);
                HandleDataSource.putDataSource(dataSource.value());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
;