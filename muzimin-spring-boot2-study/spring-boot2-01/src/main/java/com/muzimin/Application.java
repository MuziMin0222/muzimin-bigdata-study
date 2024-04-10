package com.muzimin;

import com.muzimin.config.MyConfiguration;
import com.muzimin.pojo.Pet;
import com.muzimin.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: 李煌民
 * @date: 2024-03-27 11:48
 * 主程序类
 **/
//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.muzimin")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        System.out.println("user---->" + run.getBean("zhangsan"));
        System.out.println("pet---->" + run.getBean("tomcat"));


        Pet tom01 = run.getBean("tomcat", Pet.class);
        Pet tom02 = run.getBean("tomcat", Pet.class);
        System.out.println("组件："+(tom01 == tom02));

        MyConfiguration bean = run.getBean(MyConfiguration.class);
        System.out.println(bean);

        //如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
        //无论获取多少次，都保持组件单实例
        User user = bean.zhangsan();
        User user1 = bean.zhangsan();
        System.out.println(user == user1);

        User user01 = run.getBean("zhangsan", User.class);
        Pet tom = run.getBean("tomcat", Pet.class);
        System.out.println("用户的宠物："+(user01.getPet() == tom));

        String[] beanNamesForType = run.getBeanNamesForType(User.class);
        for (String s : beanNamesForType) {
            System.out.println("--->" + s);
        }
        String[] beanNamesForType1 = run.getBeanNamesForType(Pet.class);
        for (String s : beanNamesForType1) {
            System.out.println("===>" + s);
        }

        // true
        System.out.println(run.containsBean("hhh"));
    }
}
