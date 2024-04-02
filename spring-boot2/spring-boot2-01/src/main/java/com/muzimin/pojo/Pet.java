package com.muzimin.pojo;

/**
 * @author: 李煌民
 * @date: 2024-03-28 11:25
 **/
public class Pet {
    private String Name;

    @Override
    public String toString() {
        return "Pet{" +
                "Name='" + Name + '\'' +
                '}';
    }

    public Pet() {
    }

    public Pet(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
