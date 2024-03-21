package com.muzimin.pojo;

/**
 * @author: 李煌民
 * @date: 2024-01-09 15:40
 **/
public class Emp {
    private int eid;
    private String name;
    private String email;
    private int age;
    private Dept dept;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Emp() {
    }

    public Emp(int eid, String name, String email, int age, Dept dept) {
        this.eid = eid;
        this.name = name;
        this.email = email;
        this.age = age;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", dept=" + dept +
                '}';
    }
}
