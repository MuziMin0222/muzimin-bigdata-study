package com.muzimin;

import com.muzimin.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 李煌民
 * @date: 2023-08-15 17:20
 **/
public class Application {
    @Test
    public void test01() throws IOException {
        //加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = builder.build(is);
        SqlSession sqlSession = sessionFactory.openSession();

        //获取Mapper接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.insertUser();

        //提交事务
        sqlSession.commit();
        System.out.println(i);
    }

    @Test
    public void testCrud() throws IOException {
        //加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = builder.build(is);
        SqlSession sqlSession = sessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //mapper.updateUser();
        //mapper.deleteUser();
        //System.out.println(mapper.selectById());
        mapper.selectAll().forEach(System.out::println);
    }
}
