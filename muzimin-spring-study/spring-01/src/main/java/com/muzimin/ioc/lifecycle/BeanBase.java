package com.muzimin.ioc.lifecycle;

/**
 * @author: 李煌民
 * @date: 2023-01-29 11:37
 **/
public class BeanBase {
    private String name;

    public BeanBase() {
        System.out.println("第一步：通过构造器创建bean实例");
    }

    public void setName(String name) {
        System.out.println("第二步：通过set方法为对象进行赋值");
        this.name = name;
    }

    //初始化方法
    public void initMethod(){
        System.out.println("第三步：调用bean的初始化方法");
    }

    //销毁方法
    public void destroyMethod(){
        System.out.println("第五步：对象销毁方法");
    }
}
