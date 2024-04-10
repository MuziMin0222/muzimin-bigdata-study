package com.muzimin.ioc.autowrite;

/**
 * @author: 李煌民
 * @date: 2023-01-30 10:11
 **/
public class Emp {
    private String eName;
    private Dept dept;

    public void seteName(String eName) {
        this.eName = eName;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eName='" + eName + '\'' +
                ", dept=" + dept +
                '}';
    }
}
