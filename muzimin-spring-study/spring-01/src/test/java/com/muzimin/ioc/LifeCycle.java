package com.muzimin.ioc;

import com.muzimin.ioc.lifecycle.BeanBase;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-01-29 14:17
 **/
public class LifeCycle {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("LiftCycle.xml");
        BeanBase lc = context.getBean("lc", BeanBase.class);
        System.out.println("第四步：bean可以使用了" + lc);

        context.close();
    }
}
