package com.lhm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

/**
 * @author : 李煌民
 * @date : 2021-04-06 16:10
 **/
public class RedisClusterTest {
    private JedisCluster jedisCluster;

    @Before
    public void before() {
        HashSet<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("hadoop01", 7001));
        set.add(new HostAndPort("hadoop01", 7002));
        set.add(new HostAndPort("hadoop02", 7001));
        set.add(new HostAndPort("hadoop02", 7002));
        set.add(new HostAndPort("hadoop03", 7001));
        set.add(new HostAndPort("hadoop03", 7002));

        //jedisPoolConfig配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //指定最大空闲连接为10个
        config.setMaxIdle(10);
        //最小空闲连接为5个
        config.setMinIdle(5);
        //最大等待时间为3000毫秒
        config.setMaxWaitMillis(3000);
        //最大连接数为50
        config.setMaxTotal(50);

        jedisCluster = new JedisCluster(set, config);
    }

    @Test
    public void test() {
        jedisCluster.set("k1", "v1");
        System.out.println(jedisCluster.get("k1"));
    }

    @After
    public void after() {
        jedisCluster.close();
    }
}
