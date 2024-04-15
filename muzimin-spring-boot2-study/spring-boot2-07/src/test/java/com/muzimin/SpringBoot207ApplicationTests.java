package com.muzimin;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.muzimin.bean.User;
import com.muzimin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class SpringBoot207ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    void testRedis() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello","world");
        System.out.println(ops.get("hello"));
    }

}
