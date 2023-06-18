package com.novels.novel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 王兴
 * @date 2023/6/12 8:05
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    private final List<HandlerInterceptor> handlerInterceptorList;

    public SpringWebConfig(List<HandlerInterceptor> handlerInterceptorList) {
        this.handlerInterceptorList = handlerInterceptorList;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (CollectionUtils.isEmpty(handlerInterceptorList)) {
            return;
        }
        for (HandlerInterceptor handlerInterceptor : handlerInterceptorList) {
            registry.addInterceptor(handlerInterceptor);
        }
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
