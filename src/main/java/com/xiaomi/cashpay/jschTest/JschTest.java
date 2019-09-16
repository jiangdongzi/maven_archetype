package com.xiaomi.cashpay.jschTest;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.access.method.P;

import java.util.Properties;
import java.util.Set;

public class JschTest {

    public static void main(String[] args) throws Exception {
        JSch jSSh = new JSch();
        Session sshSession = jSSh.getSession("jiangdongzi", "10.236.93.197");
        sshSession.setPassword("1");
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(config);
        sshSession.connect();
//        getAllThread();
        System.out.println("---------------");
        ChannelExec execChannel = (ChannelExec) sshSession.openChannel("exec");

        execChannel.setCommand("ls");
        execChannel.setOutputStream(System.out);
        execChannel.setErrStream(System.err);

        execChannel.connect();
//            Thread.sleep(1000);
//            reflectThread(sshSession);
//            Thread.sleep(1000);
        System.out.println("aaaaa");
        long c = 0;
        while (c++ < Integer.MAX_VALUE);
        System.out.println("--------------------------------------");
//            getAllThread();

    }

    private static void reflectThread(Session sshSession) throws IllegalAccessException {
        Object tField = FieldUtils.readField(sshSession, "connectThread", true);
        System.out.println("aaa-----------");
        System.out.println(tField);
    }

    private static void getAllThread() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println(thread);
        }
    }
}

