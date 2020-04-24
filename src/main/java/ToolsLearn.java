import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ToolsLearn {


    final static ToolsLearn lock1 = new ToolsLearn();
    final static ToolsLearn lock2 = new ToolsLearn();
    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1000);
//        deathLock();
        int a  = 3;
        char[] memTest = new char[_1M * 347];
        while (a == 3);
        System.out.println(Arrays.toString(memTest));
    }


    public static void deathLock() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                        TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (lock2) {
                        TimeUnit.SECONDS.sleep(1000);
                        synchronized (lock1) {
                            System.out.println("helloaa");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.setName("mythread1");
        t2.setName("mythread2");
        t1.start();
        t2.start();
    }


/*    public static void deathLock() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (lock1) {
                        TimeUnit.SECONDS.sleep(1);
                        synchronized (lock2) {
                            System.out.println("hello");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (lock2) {
                        TimeUnit.SECONDS.sleep(1);
                        synchronized (lock1) {
                            System.out.println("helloaa");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.setName("mythread1");
        t2.setName("mythread2");
        t1.start();
        t2.start();
    }*/



/*    public static void deathLock1() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    lock1.lock();
                    TimeUnit.SECONDS.sleep(1);
                    lock2.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    lock2.lock();
                    TimeUnit.SECONDS.sleep(1);
                    lock1.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.setName("mythread1");
        t2.setName("mythread2");
        t1.start();
        t2.start();
    }*/

}
