import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class SpringTest {


    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void testCluster() {
        try {

            String ttt = jedisCluster.get("aaaaivyjxj123");
            System.out.println(ttt);
            if (ttt == null) {
                System.out.println("null.........");
            }

            System.out.println("hello.................");
            long s = System.currentTimeMillis();
            jedisCluster.setex("ivyjxj123", 100, "test...");
            long e = System.currentTimeMillis();
            System.out.println(e - s);
            System.out.println("-----------------------");
            Thread.sleep(1700);
            String ivyjxj123 = jedisCluster.get("ivyjxj123");
            System.out.println(ivyjxj123);
        } catch (Exception e) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(ExceptionUtils.getFullStackTrace(e));
        }

    }




    @Autowired
    private ApplicationContext appContext;

    private volatile TestBean testBean;

    @Autowired
    private void setTeBean (@Qualifier("testB") TestBean teBean) {
        this.testBean = teBean;
    }

    @Test
    public void test() throws InterruptedException {

        TestBean testA = appContext.getBean("testA", TestBean.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println("start set testBean");
                    setTeBean(testA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            if (testBean == testA) {
                System.out.println("hello");
                break;
            }
        }
    }
}
