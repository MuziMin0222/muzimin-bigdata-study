package com.muzimin.new_peculiarity;

import com.muzimin.aop.annotation.User;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-07-11 15:14
 **/
public class GenericApplicationContextDemo {
    public static void main(String[] args) {
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.refresh();
        genericApplicationContext.registerBean("user", User.class, User::new);

        User user = (User) genericApplicationContext.getBean("user");
        System.out.println(user);
    }
}
