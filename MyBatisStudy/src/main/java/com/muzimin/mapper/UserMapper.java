package com.muzimin.mapper;

import com.muzimin.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    //通过名称查找数据
    List<User> selectByName(String userName);

    //通过多个条件查找数据
    List<User> selectByNameAndAge(String userName, int age);

    List<User> selectByMap(Map<String, Object> map);

    int insertUserClass(User user);

    List<User> selectByParams(@Param("userName") String name, @Param("age") String age);

    Map<String, Object> selectById2Map(@Param("id") int id);

    List<Map<String, Object>> selectAll2Map();

    @MapKey("id")
    Map<String, Object> selectAll2KeyMap();


}
