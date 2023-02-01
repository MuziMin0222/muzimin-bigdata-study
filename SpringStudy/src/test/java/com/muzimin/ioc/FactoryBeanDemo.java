package com.muzimin.ioc;

import com.muzimin.ioc.bean.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-01-29 10:28
 **/
public class FactoryBeanDemo {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("FactoryBean.xml");
        User user = context.getBean("myBean", User.class);
        System.out.println(user);
    }
}
