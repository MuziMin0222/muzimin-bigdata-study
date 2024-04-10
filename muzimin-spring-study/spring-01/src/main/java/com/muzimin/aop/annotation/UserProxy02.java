package com.muzimin.aop.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: 李煌民
 * @date: 2023-02-06 16:15
 **/
@Component
@Aspect
@Order(1)
public class UserProxy02 {
    @Pointcut(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void pointAbs(){

    }

    /*
     * 配置add方法的前置通知  execution(权限配置 类的全路径.方法名(..))
     * */
    @Before(value = "pointAbs()")
    public void before(){
        System.out.println("UserProxy2 before ....");
    }
}
