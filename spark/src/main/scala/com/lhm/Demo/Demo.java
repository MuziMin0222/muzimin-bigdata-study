package com.lhm.Demo;

import static java.util.Objects.requireNonNull;

/**
 * @author : 李煌民
 * @date : 2020-10-30 17:03
 **/
public class Demo {
    public static void main(String[] args) {
        User lhm = null;
        requireNonNull(lhm, "is null");
    }
}

class User{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
