package com.muzimin.bean;

/**
 * @author: 李煌民
 * @date: 2022-06-14 16:45
 **/
public class User {
    public void add() {
        System.out.println("这是一个Add方法");
    }

    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
