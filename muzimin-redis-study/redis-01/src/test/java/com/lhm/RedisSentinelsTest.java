package com.lhm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : 李煌民
 * @date : 2021-03-11 11:03
 **/
public class RedisSentinelsTest {
    private JedisSentinelPool jedisSentinelPool;

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

        HashSet<String> set = new HashSet<>();
        set.add("hadoop01:26379");
        set.add("hadoop02:26379");
        set.add("hadoop03:26379");

        jedisSentinelPool = new JedisSentinelPool("mymaster", set, config);
    }

    @Test
    public void test() {
        Jedis jedis = jedisSentinelPool.getResource();

        Set<String> keys = jedis.keys("*");

        keys.forEach(System.out::println);
    }

    @After
    public void after() {
        jedisSentinelPool.close();
    }
}
