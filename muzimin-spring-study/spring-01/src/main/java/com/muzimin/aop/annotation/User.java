package com.muzimin.aop.annotation;

import org.springframework.stereotype.Component;

/**
 * @author: 李煌民
 * @date: 2023-02-06 15:15
 **/
//被增强的类
@Component
public class User {
    public void add() {
        System.out.println("User Add method...");
    }
}
