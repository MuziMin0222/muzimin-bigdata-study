package com.muzimin.aop.jdk_proxy.dao;

/**
 * @author: 李煌民
 * @date: 2023-02-01 17:34
 **/
public class UserDaoImpl implements UserDao{

    @Override
    public int add(int a, int b) {
        System.out.println("userDao 中的add方法");
        return a + b;
    }

    @Override
    public String update(String id) {
        System.out.println("user Dao 中的update方法");
        return "user dao" + id;
    }
}
