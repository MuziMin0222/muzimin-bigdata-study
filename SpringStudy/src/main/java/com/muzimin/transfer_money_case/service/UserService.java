package com.muzimin.transfer_money_case.service;

import com.muzimin.transfer_money_case.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 李煌民
 * @date: 2023-05-25 18:30
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void transferMoney() {
        userDao.reduceMoney();
        userDao.addMoney();
    }
}
