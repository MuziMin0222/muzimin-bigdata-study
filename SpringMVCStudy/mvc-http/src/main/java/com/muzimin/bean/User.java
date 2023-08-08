package com.muzimin.bean;

/**
 * @author: 李煌民
 * @date: 2023-08-04 17:49
 **/
public class User {
    private String name;
    private String email;
    private Integer age;
    private Integer gender;

    public User(String name, String email, Integer age, Integer gender) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
