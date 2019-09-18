package com.jschTest;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

        OutputStream os = execChannel.getOutputStream();
        InputStream is = execChannel.getInputStream();
        execChannel.setOutputStream(os);
        PrintWriter pw = new PrintWriter(os);
        pw.println("mvn clean compile");
//        execChannel.setPty(false);
        int cnt;
        while (true) {
            byte[] buf = new byte[128];
            cnt = is.read(buf);
            if (cnt == -1) {
                break;
            }
            System.out.println(new String(buf) + "::" + cnt);
        }
        System.out.println("--------------" + cnt);
        is.close();
        System.exit(0);
//        int a = 0;
//        Thread.sleep(5000);
//        execChannel.setCommand("pwd");
//        execChannel.connect();
//        System.out.println("aaa");
//        getAllThread();
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
