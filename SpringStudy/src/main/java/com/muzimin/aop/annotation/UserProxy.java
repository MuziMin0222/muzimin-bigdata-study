package com.muzimin.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: 李煌民
 * @date: 2023-02-06 15:16
 **/
//增加的类
@Component
@Aspect    //表示生成一个代理对象
@Order(3)
public class UserProxy {
    @Pointcut(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void pointAbs(){

    }

    /*
    * 配置add方法的前置通知  execution(权限配置 类的全路径.方法名(..))
    * */
    @Before(value = "pointAbs()")
    public void before(){
        System.out.println("before ....");
    }

    /*
    * 配置add方法的后置通知
    * */
    @AfterReturning(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void afterReturning(){
        System.out.println("AfterReturning....");
    }

    /*
     * 配置add方法的异常通知
     * 只有遇到异常才会执行
     * */
    @AfterThrowing(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void afterThrowing(){
        System.out.println("AfterThrowing....");
    }

    /*
     * 配置add方法的环绕通知
     * */
    @Around(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void afterThrowing(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Around之前。。。");
        proceedingJoinPoint.proceed();
        System.out.println("Around之后。。。");
    }

    /*
     * 配置add方法的最终通知，无论add方法是否有异常，都执行
     * */
    @After(value = "execution(* com.muzimin.aop.annotation.User.add(..))")
    public void after() {
        System.out.println("After。。。");
    }

}
