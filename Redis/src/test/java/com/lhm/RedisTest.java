package com.lhm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * @author : 李煌民
 * @date : 2021-03-09 12:06
 **/
public class RedisTest {
    private JedisPool jedisPool;

    @Before
    public void before() {
        JedisPoolConfig config = new JedisPoolConfig();

        //指定最大空闲连接
        config.setMaxIdle(10);
        //最小空闲连接
        config.setMinIdle(5);
        //最大等待时间
        config.setMaxWaitMillis(3000);
        //最大连接数
        config.setMaxTotal(50);

        jedisPool = new JedisPool(config, "hadoop01", 6379);
    }

    @Test
    public void test() {
        Jedis jedis = jedisPool.getResource();

        Set<String> keys = jedis.keys("*");

        for (String key : keys) {
            System.out.println(key);
        }

        jedis.close();
    }

    @Test
    public void stringTest() {
        Jedis jedis = jedisPool.getResource();

        //添加元素
        jedis.set("pv", "0");

        System.out.println(jedis.get("pv"));

        //修改
        jedis.set("pv", "1000");

        //自增1
        jedis.incr("pv");

        //加上指定的1000
        jedis.incrBy("pv", 1000);

        System.out.println(jedis.get("pv"));

        jedis.close();
    }

    @Test
    public void hashTest() {
        Jedis jedis = jedisPool.getResource();

        //添加元素
        jedis.hset("goods", "iphone11", "10000");
        jedis.hset("goods", "macBookpro", "9000");

        //获取所有指定key的值
        Set<String> goods = jedis.hkeys("goods");
        for (String good : goods) {
            System.out.println("key:goods," + "field:" + good + ",value:" + jedis.hget("goods", good));
        }

        // 新增3000macBookpro的库存,不能先get，再set，不是原子操作
        jedis.hincrBy("goods", "macbookpro", 3000);

        //删除hash的数据
        jedis.del("goods");

        jedis.close();
    }

    @Test
    public void listTest() {
        Jedis jedis = jedisPool.getResource();

        //向左边插入三个手机号码
        jedis.lpush("tel_list", "12345678", "123456789", "1234567891");

        //移除右边的第一个元素
        String rpop = jedis.rpop("tel_list");
        System.out.println("右边的第一个元素：" + rpop);

        //获取list的所有元素
        List<String> tel_list = jedis.lrange("tel_list", 0, -1);
        for (String tel : tel_list) {
            System.out.println(tel);
        }

        jedis.close();
    }

    @Test
    public void setTest() {
        Jedis jedis = jedisPool.getResource();

        jedis.sadd("uv", "page1", "page2");
        jedis.sadd("uv", "page3");
        jedis.sadd("uv", "page1");

        Long uv = jedis.scard("uv");
        System.out.println("uv:" + uv);

        jedis.close();
    }

    @After
    public void after() {
        jedisPool.close();
    }
}
