package com.muzimin.aop.configfile;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-02-17 16:32
 **/
public class ApplicationDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop-configfile-bean-01.xml");
        Book book = context.getBean("book", Book.class);
        book.buy();
    }
}
