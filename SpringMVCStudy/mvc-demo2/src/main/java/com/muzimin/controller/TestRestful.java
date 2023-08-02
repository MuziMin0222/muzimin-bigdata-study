package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: 李煌民
 * @date: 2023-08-02 15:51
 **/
@Controller
public class TestRestful {
    @RequestMapping(value = "/testRestFul")
    public String testRestFul() {
        return "testRestFul";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUsers() {
        System.out.println("获取所有的用户");
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(@PathVariable Integer id) {
        System.out.println("获取id为" + id + "的用户");
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser(String userName, String password) {
        System.out.println("保存用户信息：" + userName + "---->" + password);
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser(String userName, String password) {
        System.out.println("更新用户信息：" + userName + "---->" + password);
        return "success";
    }
}
