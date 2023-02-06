package com.muzimin.aop.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-02-06 15:32
 **/
public class ApplicationDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop-annotation-bean-01.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }
}
