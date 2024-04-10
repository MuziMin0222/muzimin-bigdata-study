package com.muzimin.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: 李煌民
 * @date: 2023-02-17 17:15
 **/
@Configuration
@ComponentScan(basePackages = {"com.muzimin"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig {
}
