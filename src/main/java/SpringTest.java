import com.xiaomi.cashpay.async.service.AClazz;
import com.xiaomi.cashpay.async.service.BClazz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class SpringTest {

    @Autowired
    AClazz aClazz;

    @Autowired
    BClazz bClazz;

    @Test
    public void testA() {
        System.out.println(aClazz.getA());
        System.out.println(aClazz.getbClazz().getB());
    }
}
