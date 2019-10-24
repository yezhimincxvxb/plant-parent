package com.moguying.plant.core.aop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.moguying.plant.core.entity.PageResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class PaginationAop {

    Logger log = LoggerFactory.getLogger(PaginationAop.class);


    @Pointcut("@annotation(com.moguying.plant.core.annotation.Pagination)")
    private void pagination(){

    }


    @Around("pagination()")
    private <T> PageResult<T> addPagination(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        try {
            Page pageHelp = PageHelper.startPage((int)args[0],(int)args[1]);
            proceedingJoinPoint.proceed(args);
            int total = (int) pageHelp.getTotal();
            return new PageResult<T>(total,pageHelp.getResult());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }






}
