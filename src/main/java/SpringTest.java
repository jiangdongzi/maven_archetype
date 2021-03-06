import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class SpringTest {

    @Autowired
    private TestBean testBean;
    @Test
    public void test()
    {
        System.out.println(testBean.getId());
        System.out.println(testBean.getName());
    }
}
