package com.muzimin.ioc.annotation.service;

import com.muzimin.ioc.annotation.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 李煌民
 * @date: 2023-02-01 14:27
 **/
@Service(value = "userService")
public class UserService {

    @Value(value = "abc")
    private String name;

    /*
    * 不需要添加set方法
    * */
    /*@Autowired
    @Qualifier(value = "userDaoImpl2")*/
    @Resource(name = "userDaoImpl2")
    private UserDao userDao;

    public void add(){
        System.out.println("this is a add method ===> " + name);
        userDao.add();
    }

}
