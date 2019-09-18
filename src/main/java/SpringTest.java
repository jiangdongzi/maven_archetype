import com.xiaomi.cashpay.mq.Constants;
import com.xiaomi.cashpay.mq.listener.XMessageListenerContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class SpringTest {

    public static XMessageListenerContainer toBeStart;

    @Autowired
    private TestBean testBean;
    @Test
    public void test() throws InterruptedException {
//        Constants.toBeStart.start();
        System.out.println(testBean.getId());
        System.out.println(testBean.getName());
        System.out.println("--------------------------------------");
        System.out.println("test thread Name = " + Thread.currentThread());
        Thread.sleep(100000);
        testBean.setId(1);
    }
}
