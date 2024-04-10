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
        try {
            //开启事务
            //xxx

            //业务操作
            userDao.reduceMoney();

            int i = 1 / 0;

            userDao.addMoney();

            //没有异常，提交事物
        } catch (Exception e) {
            // 出现异常，事务回滚
            e.printStackTrace();
        }

    }
}
