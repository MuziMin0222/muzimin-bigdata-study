package com.muzimin.ioc.factory_bean;

import com.muzimin.ioc.bean.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: 李煌民
 * @date: 2023-01-29 10:25
 **/
public class MyBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setAge(111);
        user.setName("这是一个工厂Bean创建的对象");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
