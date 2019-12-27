package com.moguying.plant.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware , DisposableBean {

    private static ApplicationContext applicationContext;

    /**
     * 实现 ApplicationContextAware 接口，注入 Context 到静态变量中
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取存储在静态变量中的 ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据字节码对象获取对应的Bean对象
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }


    /**
     * 实现 DisposableBean 接口，在 Context 关闭时清理静态变量
     */
    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}
