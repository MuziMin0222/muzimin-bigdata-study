package com.muzimin.aop.jdk_proxy;

import com.muzimin.aop.jdk_proxy.dao.UserDao;
import com.muzimin.aop.jdk_proxy.dao.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author: 李煌民
 * @date: 2023-02-01 17:36
 **/
public class DynamicProxyDemo {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();

        //这里利用反射，对原方法进行增强
        UserDao user = (UserDao)Proxy.newProxyInstance(
                DynamicProxyDemo.class.getClassLoader(),
                new Class[]{UserDao.class},
                (proxy, method, args1) -> {
                    System.out.println("原方法" + method.getName() + "执行之前的逻辑, 参数列表为" + Arrays.toString(args1));

                    Object res = method.invoke(userDao, args1);

                    System.out.println("原方法" + method.getName() + "执行后的逻辑。。。。");

                    return res;
                }
        );

        System.out.println(user.add(1, 2));
    }
}
