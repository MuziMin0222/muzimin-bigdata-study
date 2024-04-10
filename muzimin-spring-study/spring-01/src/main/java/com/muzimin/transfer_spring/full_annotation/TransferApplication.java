package com.muzimin.transfer_spring.full_annotation;

import com.muzimin.transfer_spring.full_annotation.config.Config;
import com.muzimin.transfer_spring.full_annotation.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-07-03 13:53
 **/
public class TransferApplication {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class
        );
        UserService userService = context.getBean("userService", UserService.class);

        userService.transferMoney();
    }
}
