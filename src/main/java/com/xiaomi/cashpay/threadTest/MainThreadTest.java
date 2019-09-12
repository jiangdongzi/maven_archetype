package com.xiaomi.cashpay.threadTest;

import org.junit.Test;

import java.util.Set;

import static java.lang.Thread.sleep;

public class MainThreadTest {

    @Test
    public void testThread() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(new LoopTask());
                thread.start();
                try {
                    sleep(4000);
                    System.out.println("end of nest thread, thread = " + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        Thread.sleep(888);
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println(thread);
        }


        System.out.println("------------------tName = ------------" + t);
        sleep(5000);
        System.out.println(t.isAlive());
//        t.interrupt();
        Thread.sleep(1000);
        System.out.println(t.isAlive());
        System.out.println("interrupt thread");
        System.out.println("分界线");
        threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println(thread);
        }
        sleep(12000);
        System.out.println("----------end--------");
    }


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new LoopTask());
        thread.start();
        sleep(8000);
        System.out.println("----------end--------");
    }


}

class LoopTask implements Runnable {
    private int c;
    @Override
    public void run() {
        while (true) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("c = " + c);
        }
    }
}
