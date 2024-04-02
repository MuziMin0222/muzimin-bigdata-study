package com.muzimin.config;

import com.muzimin.pojo.Car;
import com.muzimin.pojo.Pet;
import com.muzimin.pojo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: 李煌民
 * @date: 2024-03-28 11:27
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 * Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 * Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 * 组件依赖必须使用Full模式默认。其他默认是Lite模式
 **/
//该注解告诉SpringBoot这是一个配置类，相当于Spring中的配置文件
@Configuration(proxyBeanMethods = true)
//Import注解可以给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
@Import({User.class, Pet.class})
//当环境中存在Tom组件时，才进行下面所有的进行装配，可以作用到类上，也可以作用到方法上
//@ConditionalOnBean(name = "tomcat")
// 可以对原生Spring配置文件进行加载到配置类中
@ImportResource("classpath:bean.xml")
@EnableConfigurationProperties(Car.class)
public class MyConfiguration {

    //Full: 外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
    // 在获取对象名时，默认是使用方法名进行获取
    @Bean
    public User zhangsan() {
        User zhangsan = new User("zhangsan");
        zhangsan.setPet(tom());
        return zhangsan;
    }

    // 获取对象时，也可以通过bean中进行重命名，这样就不需要通过方法名进行获取了
    @Bean("tomcat")
    public Pet tom() {
        return new Pet("Tom");
    }
}
