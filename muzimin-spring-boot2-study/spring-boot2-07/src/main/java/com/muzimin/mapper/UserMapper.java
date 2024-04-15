package com.muzimin.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muzimin.bean.User;
import com.muzimin.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 李煌民
 * @date: 2024-04-15 14:37
 **/
@Mapper
@TableName("user")
public interface UserMapper extends BaseMapper<User> {

}
