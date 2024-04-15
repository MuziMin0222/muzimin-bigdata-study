package com.muzimin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzimin.bean.User;
import com.muzimin.mapper.UserMapper;
import com.muzimin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: 李煌民
 * @date: 2024-04-15 15:05
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
