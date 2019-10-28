package com.moguying.plant.core.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.moguying.plant.core.interceptor.ApiInterceptor;
import com.moguying.plant.core.interceptor.BackInterceptor;
import com.moguying.plant.core.interceptor.CallBackInterceptor;
import com.moguying.plant.core.resolver.LoginUserIdMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LoginUserIdMethodArgumentResolver argumentResolver;


    @Autowired
    private ApiInterceptor apiInterceptor;

    @Autowired
    private BackInterceptor backInterceptor;

    @Autowired
    private CallBackInterceptor callBackInterceptor;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentResolver);
    }


    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        return multipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        messageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(messageConverter);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor).addPathPatterns("/api/account/**","/api/user/**","/api/seed/**", "/api/mall/**","/api/farmer/**");
        registry.addInterceptor(backInterceptor).addPathPatterns("/backEnd/**");
        registry.addInterceptor(callBackInterceptor).addPathPatterns("/payment/notify/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(true)
                .addPathPrefix("/backEnd", HandlerTypePredicate.forBasePackage("com.moguying.plant.core.controller.back"))
                .addPathPrefix("/api",HandlerTypePredicate.forBasePackage("com.moguying.plant.core.controller.api"));
    }
}
