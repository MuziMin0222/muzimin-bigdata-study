package com.muzimin.transfer_spring.xml_file;

import com.muzimin.transfer_spring.xml_file.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-07-03 13:53
 **/
public class TransferApplication {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("transfer-spring-xmlfile.xml");
        UserService userService = context.getBean("userService", UserService.class);

        userService.transferMoney();
    }
}
