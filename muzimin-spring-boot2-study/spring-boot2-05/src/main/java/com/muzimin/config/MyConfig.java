package com.muzimin.config;

import com.muzimin.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 李煌民
 * @date: 2024-04-11 20:16
 * 1.将拦截器注册到容器中（实现WebMvcConfigurer中的addInterceptors方法）
 * 2.指定拦截器的拦截规则，/**是拦截所有，包括静态资源
 **/
@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")//所有请求都会被拦截，包括静态资源
                .excludePathPatterns("/", "/login", "/css/**", "fonts/**", "/images/**", "/js/**", "/error");
    }
}
