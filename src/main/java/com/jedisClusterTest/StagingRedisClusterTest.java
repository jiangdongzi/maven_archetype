package com.jedisClusterTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

public class StagingRedisClusterTest {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        JedisCluster jed = ctx.getBean("jedisCluster", JedisCluster.class);
        String s = jed.get("cached-userId-by-outId-1-2201852814");
        System.out.println(s);
    }
}
