package com.muzimin.transfer_money_case;

import com.muzimin.jdbc_template.service.BookService;
import com.muzimin.transfer_money_case.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author: 李煌民
 * @date: 2023-07-03 13:53
 **/
public class TransferApplication {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("transfer-money.xml");
        UserService userService = context.getBean("userService", UserService.class);

        userService.transferMoney();
    }
}
