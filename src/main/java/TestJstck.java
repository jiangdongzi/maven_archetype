public class TestJstck {


    private static TestJstck thisClazz = new TestJstck();

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    synchronized (thisClazz) {
                        Thread.sleep(500);
                        System.out.println("hello");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        synchronized (thisClazz) {
            while (true) {
                thisClazz = null;
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());

            }
        }
    }

    public void testS() {
        int c = 0;
        while (c < 100) {
            c *= 10;
        }
        System.out.println(c);
    }
}
