package com.jedisClusterTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class StagingRedisClusterTest {

    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void testJedis() {
        jedisCluster.setex("cached-userId-by-outId-1-2201852814", 1000, "hello");
        System.out.println(jedisCluster.get("cached-userId-by-outId-1-2201852814"));
        System.out.println(jedisCluster.exists("cached-userId-by-outId-1-2201852814"));
    }
}
