package com.muzimin;

import com.muzimin.bean.Order;
import com.muzimin.bean.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2022-06-14 17:06
 **/
public class Demo01 {

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        User user = context.getBean("user", User.class);

        user.add();

        System.out.println(user.getName() + "----" + user.getAge());

        Order order = context.getBean("order", Order.class);
        System.out.println(order.toString());

        User user01 = context.getBean("user01", User.class);
        System.out.println(user01.toString());

        Order order01 = context.getBean("order01", Order.class);
        System.out.println(order01.toString());
    }
}
