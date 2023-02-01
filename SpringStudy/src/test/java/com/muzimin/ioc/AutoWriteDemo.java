package com.muzimin.ioc;

import com.muzimin.ioc.autowrite.Emp;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2023-01-30 10:12
 **/
public class AutoWriteDemo {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AutoWrite.xml");
        Emp emp = context.getBean("emp", Emp.class);

        System.out.println(emp);
    }
}
