package com.muzimin.mapper;

import com.muzimin.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2023-12-18 15:24
 **/
public interface SQLMapper {
    List<Map<String, String>> selectByName(@Param("userName") String username);

    int deleteByIds(@Param("ids") String ids);

    List<User> selectAll(@Param("tableName") String tableName);

    int inertUser(User user);
}
