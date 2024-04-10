package com.muzimin.config;

import com.muzimin.interceptors.MyInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author: 李煌民
 * @date: 2023-08-08 16:34
 * springMvc 的配置文件替代
 * 1、组件扫描
 * 2、视图解析器
 * 3、view-controller
 * 4、default-servlet-handler
 * 5、mvc注解驱动
 * 6、文件上传解析器
 * 7、异常处理
 * 8、拦截器
 **/
@Configuration
//组件扫描
@ComponentScan("com.muzimin.controller")
//mvc注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //开启default-servlet-handler
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptors()).addPathPatterns("/**").excludePathPatterns("/");
    }

    //view-controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
    }

    //文件上传解析器
    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    //异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("java.lang.ArithmeticException", "error");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        simpleMappingExceptionResolver.setExceptionAttribute("exception");
        resolvers.add(simpleMappingExceptionResolver);
    }

    //配置生成模版解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        assert webApplicationContext != null;
        ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver(Objects.requireNonNull(webApplicationContext.getServletContext()));
        servletContextTemplateResolver.setPrefix("/WEB-INF/templates/");
        servletContextTemplateResolver.setSuffix(".html");
        servletContextTemplateResolver.setCharacterEncoding("UTF-8");
        servletContextTemplateResolver.setTemplateMode(TemplateMode.HTML);
        return servletContextTemplateResolver;
    }

    //生成模版引擎并为模版引擎注入模版解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver);

        return springTemplateEngine;
    }

    //生成视图解析器并为解析器注入模版引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);

        return thymeleafViewResolver;
    }
}
