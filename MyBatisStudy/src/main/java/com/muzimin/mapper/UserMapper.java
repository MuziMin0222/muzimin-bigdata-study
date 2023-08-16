package com.muzimin.mapper;

import com.muzimin.pojo.User;

import java.util.List;

/**
 * @author: 李煌民
 * @date: 2023-08-15 16:43
 **/
public interface UserMapper {
    /*
    * 添加用户信息
    * */
    int insertUser();

    /**
     * 更新用户信息
     */
    void updateUser();

    /**
     * 删除用户信息
     */
    void deleteUser();

    /**
     * 根据ID查询信息
     */
    User selectById();

    //查询所有用户
    List<User> selectAll();
}
