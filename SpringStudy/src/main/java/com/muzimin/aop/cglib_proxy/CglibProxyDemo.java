package com.muzimin.aop.cglib_proxy;

import com.muzimin.aop.cglib_proxy.dao.UserDao;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: 李煌民
 * @date: 2023-02-01 17:59
 **/
public class CglibProxyDemo {
    public static void main(String[] args) {

        UserDao user = new UserDao();

        CglibProxy proxy = new CglibProxy();

        UserDao userNew = (UserDao) proxy.getInstance(user);
        System.out.println(userNew.add(1, 2));
    }
}

class CglibProxy implements MethodInterceptor {

    private Object target;//代理对象

    public Object getInstance(Object obj) {
        this.target = obj;

        Enhancer enhancer = new Enhancer();
        //设置父类为实例类
        enhancer.setSuperclass(this.target.getClass());
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用原方法" + method.getName() + "之前增加的业务逻辑, 原方法参数为：" + Arrays.toString(objects));

        //Object result = methodProxy.invokeSuper(o, objects);
        Object result = method.invoke(target, objects);

        System.out.println("调用原方法" + method.getName() + "之后增加的业务逻辑");

        return result;
    }
}
