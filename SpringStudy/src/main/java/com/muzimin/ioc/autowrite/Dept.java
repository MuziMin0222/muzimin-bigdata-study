package com.muzimin.ioc.autowrite;

/**
 * @author: 李煌民
 * @date: 2023-01-30 10:11
 **/
public class Dept {
    private String dName;

    public void setdName(String dName) {
        this.dName = dName;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dName='" + dName + '\'' +
                '}';
    }
}
