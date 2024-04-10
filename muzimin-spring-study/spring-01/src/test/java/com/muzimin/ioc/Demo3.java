package com.muzimin.ioc;

import com.muzimin.ioc.bean.CollectionBean;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 李煌民
 * @date: 2022-06-15 17:21
 **/
public class Demo3 {
    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");

        CollectionBean collection = context.getBean("collection", CollectionBean.class);

        System.out.println(collection);
    }

}
