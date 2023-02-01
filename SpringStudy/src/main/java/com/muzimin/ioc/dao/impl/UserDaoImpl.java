package com.muzimin.ioc.dao.impl;

import com.muzimin.ioc.dao.UserDao;

/**
 * @author: 李煌民
 * @date: 2022-06-15 15:06
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public void update() {
        System.out.println("this is a update method");
    }
}
