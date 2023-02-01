package com.muzimin.ioc.annotation.dao;

import org.springframework.stereotype.Repository;

/**
 * @author: 李煌民
 * @date: 2023-02-01 15:06
 **/
@Repository(value = "userDaoImpl2")
public class UserDaoImpl2 implements UserDao{

    @Override
    public void add() {
        System.out.println("this is a user dao 2 add method");
    }
}
