package com.muzimin.ioc.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 李煌民
 * @date: 2023-02-01 16:06
 **/
@Configuration
@ComponentScan(basePackages = {"com.muzimin.aop"})
public class SpringConfig {
}
