package com.muzimin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muzimin.mapper.*;
import com.muzimin.pojo.Dept;
import com.muzimin.pojo.Emp;
import com.muzimin.pojo.User;
import com.muzimin.reverse.entity.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void testParameter() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //System.out.println(mapper.selectByName("muzimin"));
        //System.out.println(mapper.selectByNameAndAge("muzimin", 123));
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", "muzimin");
        map.put("age", "123");
        System.out.println(mapper.selectByMap(map));
    }

    @Test
    public void testClassParameter() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.insertUserClass(new User(null, "实体类插入", "35岁", "男")));
    }

    @Test
    public void testParameterAn() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectByParams("aaa", "123"));
    }

    @Test
    public void testSelectById2Map() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectById2Map(3));
    }

    @Test
    public void testSelectAll2Map() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //[{gender=男, name=muzimin, id=1, age=123}, {gender=男, name=aaa, id=3, age=123}, {gender=男, name=aaa, id=4, age=123}, {gender=男, name=实体类插入, id=5, age=35岁}, {gender=男, name=实体类插入, id=6, age=35岁}]
        System.out.println(mapper.selectAll2Map());
    }

    @Test
    public void testSelectAll2KeyMap() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //{1={gender=男, name=muzimin, id=1, age=123}, 3={gender=男, name=aaa, id=3, age=123}, 4={gender=男, name=aaa, id=4, age=123}, 5={gender=男, name=实体类插入, id=5, age=35岁}, 6={gender=男, name=实体类插入, id=6, age=35岁}}
        System.out.println(mapper.selectAll2KeyMap());
    }

    @Test
    public void testSelectByName() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        //[{gender=男, name=aaa, id=3, age=123}, {gender=男, name=aaa, id=4, age=123}]
        System.out.println(mapper.selectByName("a"));
    }

    @Test
    public void testDeleteByIds() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        //[{gender=男, name=aaa, id=3, age=123}, {gender=男, name=aaa, id=4, age=123}]
        System.out.println(mapper.deleteByIds("4,5,6"));
    }

    @Test
    public void testSelectAll() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        //[{gender=男, name=aaa, id=3, age=123}, {gender=男, name=aaa, id=4, age=123}]
        System.out.println(mapper.selectAll("t_user"));
    }

    @Test
    public void testInserUser() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        SQLMapper mapper = sqlSession.getMapper(SQLMapper.class);
        User user = new User(null, "aaa", "15", "nan");
        System.out.println(mapper.inertUser(user));
        System.out.println(user);
    }

    @Test
    public void testEmpAndDept() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        ResultMapper mapper = sqlSession.getMapper(ResultMapper.class);
        //[Emp{eid=1, name='aaa', email='1234@qq.com', age=14, dept=Dept{did=1, dName='Hadoop'}}]
        System.out.println(mapper.selectEmpById(1));
    }


    @Test
    public void testEmpAndDeptByStep() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        //[Emp{eid=1, name='aaa', email='1234@qq.com', age=14, dept=Dept{did=1, dName='Hadoop'}}]
        System.out.println(mapper.selectByEid(1));
    }

    @Test
    public void testEmpAndDeptByStepLazy() throws IOException {

        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        //[Emp{eid=1, name='aaa', email='1234@qq.com', age=14, dept=Dept{did=1, dName='Hadoop'}}]
        Emp emp = mapper.selectByEid(1);
        /*
         *  开启延迟加载后只执行这一条SQL语句
         *  24/01/11 10:51:17 DEBUG selectByEid: ==>  Preparing: select * from t_emp where e_id = 1
         * */
        System.out.println(emp.getName());
        System.out.println("===================");
        System.out.println(emp.getDept());
    }

    @Test
    public void testDeptAndEmps() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        ResultMapper mapper = sqlSession.getMapper(ResultMapper.class);
        Dept deptAndEmp = mapper.getDeptAndEmp(1);
        //Dept{did=1, dName='Hadoop', emps=[Emp{eid=1, name='aaa', email='1234@qq.com', age=14, dept=null}, Emp{eid=3, name='ccc', email='1234@qq.com', age=16, dept=null}]}
        System.out.println(deptAndEmp);
    }


    @Test
    public void testDeptAndEmpsByStep() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept deptAndEmp = mapper.getDeptAndEmpsStepOne(1);
        //Dept{did=1, dName='Hadoop', emps=[Emp{eid=1, name='aaa', email='1234@qq.com', age=14, dept=null}, Emp{eid=3, name='ccc', email='1234@qq.com', age=16, dept=null}]}
        System.out.println(deptAndEmp);
    }

    @Test
    public void testDynamicSql() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
        //select * from t_emp where 1 = 1
        //List<Emp> emps = mapper.getEmps(new Emp(1, null, null, 2, null));
        //select * from t_emp where 1 = 1 and name = ?
        //List<Emp> emps = mapper.getEmps(new Emp(1, "aaa", null, 2, null));
        //select * from t_emp where 1 = 1 and name = ? and email = ?
        List<Emp> emps = mapper.getEmps(new Emp(1, "aaa", "1234@qq.com", 2, null));

        System.out.println(emps);
    }

    @Test
    public void testDynamicSql2() throws IOException {
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
        //select * from t_emp WHERE e_id = ? or e_id = ? or e_id = ?
        List<Emp> emps = mapper.getEmpsByList(new Integer[]{1, 2, 3});

        System.out.println(emps);
    }

    @Test
    public void testCache() throws IOException {
        SqlSession sqlSession1 = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);

        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        System.out.println(mapper1.getEmp(1));

        sqlSession1.clearCache();

        SqlSession sqlSession2 = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"))
                .openSession(true);
        CacheMapper mapper2 = sqlSession1.getMapper(CacheMapper.class);
        System.out.println(mapper2.getEmp(1));
    }

    @Test
    public void testCache2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = factoryBuilder.build(resourceAsStream);

        SqlSession sqlSession1 = build.openSession(true);
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        System.out.println(mapper1.getEmp(1));
        sqlSession1.close();

        SqlSession sqlSession2 = build.openSession(true);
        CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
        System.out.println(mapper2.getEmp(1));
        sqlSession2.close();
    }

    @Test
    public void testReverse() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(is)
                .openSession(true);

        com.muzimin.reverse.mapper.EmpMapper mapper = sqlSession.getMapper(com.muzimin.reverse.mapper.EmpMapper.class);
        EmpExample empExample = new EmpExample();
        empExample.createCriteria().andAgeBetween(1, 1000);
        empExample.or().andEmailEqualTo("aaa");
        System.out.println(mapper.selectByExample(empExample));

    }

    @Test
    public void testLimit() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSession sqlSession = new SqlSessionFactoryBuilder()
                .build(is)
                .openSession(true);

        com.muzimin.reverse.mapper.EmpMapper mapper = sqlSession.getMapper(com.muzimin.reverse.mapper.EmpMapper.class);
        EmpExample empExample = new EmpExample();

        //开启分页
        PageHelper.startPage(2, 3);

        empExample.createCriteria().andAgeBetween(1, 1000);
        empExample.or().andEmailEqualTo("aaa");
        List<com.muzimin.reverse.entity.Emp> empList = mapper.selectByExample(empExample);

        /**
         * 获取分页相关信息
         *    list: 表示分页数据
         *    3： 表示导航分页的数量
         */
        PageInfo<com.muzimin.reverse.entity.Emp> pageInfo = new PageInfo<>(empList, 3);

        System.out.println(pageInfo);
    }
}
