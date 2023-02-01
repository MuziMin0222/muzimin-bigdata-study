package com.muzimin.ioc;

import com.muzimin.ioc.bean.Employee;
import com.muzimin.ioc.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2022-06-15 15:17
 **/
public class Demo2 {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");

        UserService service = context.getBean("userService", UserService.class);

        service.add();

        Employee emp = context.getBean("emp", Employee.class);
        System.out.println(emp);

        Employee emp01 = context.getBean("emp01", Employee.class);
        System.out.println(emp01);
    }
}
