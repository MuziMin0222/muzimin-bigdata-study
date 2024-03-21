package com.muzimin.pojo;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-01-09 15:41
 **/
public class Dept {
    private int did;
    private String dName;
    private List<Emp> emps;

    @Override
    public String toString() {
        return "Dept{" +
                "did=" + did +
                ", dName='" + dName + '\'' +
                ", emps=" + emps +
                '}';
    }

    public List<Emp> getEmps() {
        return emps;
    }

    public void setEmps(List<Emp> emps) {
        this.emps = emps;
    }

    public Dept(int did, String dName, List<Emp> emps) {
        this.did = did;
        this.dName = dName;
        this.emps = emps;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Dept() {
    }
}
