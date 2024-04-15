package com.muzimin.controller;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.muzimin.bean.User;
import com.muzimin.mapper.UserMapper;
import com.muzimin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2024-04-15 14:41
 **/
@RestController
public class MyBatisPlusController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/getAllUser")
    String getAllUser() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
        System.out.println(userService.list());
        return "ok";
    }
}
