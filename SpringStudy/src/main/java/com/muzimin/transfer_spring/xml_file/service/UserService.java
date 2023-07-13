package com.muzimin.transfer_spring.xml_file.service;

import com.muzimin.transfer_money_case.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 李煌民
 * @date: 2023-05-25 18:30
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void transferMoney() {
        try {
            userDao.reduceMoney();

            int i = 1 / 0;

            userDao.addMoney();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
