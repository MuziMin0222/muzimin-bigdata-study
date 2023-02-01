package com.muzimin.aop.cglib_proxy.dao;

/**
 * @author: 李煌民
 * @date: 2023-02-01 17:59
 **/
public class UserDao {
    public String add(int a, int b) {
        System.out.println("===user dao 中的方法====>");
        return "原生代码" + (a + b);
    }
}
