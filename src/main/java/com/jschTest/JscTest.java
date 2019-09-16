package com.jschTest;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.FileWriter;
import java.util.Properties;
import java.util.Set;

public class JscTest {


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

        execChannel.setCommand("ls;exit");
//        execChannel.setPty(false);
//        execChannel.setOutputStream(System.out);
//        execChannel.setErrStream(System.err);
        execChannel.connect();
        int a = 0;
        Thread.sleep(500);
        System.out.println("aaa");
        getAllThread();
//        new FileWriter("hello_jsh");
    }

//    private static void reflectThread(Session sshSession) throws IllegalAccessException {
//        Object tField = FieldUtils.readField(sshSession, "connectThread", true);
//        System.out.println("aaa-----------");
//        System.out.println(tField);
//    }

    private static void getAllThread() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println(thread);
        }
    }


}
