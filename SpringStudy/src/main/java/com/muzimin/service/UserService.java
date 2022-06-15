package com.muzimin.service;

import com.muzimin.dao.UserDao;

/**
 * @author: 李煌民
 * @date: 2022-06-15 15:04
 **/
public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(){
        System.out.println("service in add");
        userDao.update();
    }

}
