package com.jedisClusterTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

public class StagingRedisClusterTest {

    public static void main(String[] args) {
        System.out.println("cur thread = " + Thread.currentThread());
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("----------------------------------------------------------------------------");
        JedisCluster jed = ctx.getBean("jedisCluster", JedisCluster.class);
        String s = jed.get("cached-userId-by-outId-1-2201852814");
        System.out.println(s);
        System.out.println("----------------------------exit...........................");
    }

    public static void main1(String[] args) {
        Runtime.getRuntime().traceMethodCalls(true);
        Runtime.getRuntime().traceInstructions(true);
        testFunc("hello");
        testFunc("world");
    }

    private static void testFunc(String msg) {
        System.out.println(msg);
    }
}
