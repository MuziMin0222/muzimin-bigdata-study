package com.muzimin.bean;

/**
 * @author: 李煌民
 * @date: 2022-06-15 16:06
 **/
public class Dept {
    private String dName;

    public String getdName() {
        return dName;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dName='" + dName + '\'' +
                '}';
    }

    public void setdName(String dName) {
        this.dName = dName;
    }
}
