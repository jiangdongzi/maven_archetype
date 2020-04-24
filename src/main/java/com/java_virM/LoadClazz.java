package com.java_virM;

public class LoadClazz {


    private static long c;

    public static void main(String[] args) throws InterruptedException {
        Thread curThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    c++;
                    System.out.println(Thread.interrupted());
                }
            }
        });
        curThread.start();
        System.out.println(curThread.getState());
        Thread.sleep(1000);

        curThread.interrupt();
        while (true) {
//            System.out.println(Thread.interrupted());
//            System.out.println(c);
//            System.out.println(curThread.getState());
            Thread.sleep(1000);
        }
    }




}
