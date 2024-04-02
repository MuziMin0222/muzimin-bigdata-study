package com.muzimin.pojo;

/**
 * @author: 李煌民
 * @date: 2024-03-28 11:25
 **/
public class User {
    private String Name;
    private Pet pet;

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", pet=" + pet +
                '}';
    }

    public User(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User() {
    }

    public User(String name, Pet pet) {
        Name = name;
        this.pet = pet;
    }
}
