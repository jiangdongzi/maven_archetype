import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class SpringTest {

    @Autowired
    JedisCluster jedisCluster;

    @Test
    public void testSpring() {
        String abc = jedisCluster.get("abc");
        jedisCluster.setex("abc", 10, "ivy");
        System.out.println(abc);
    }
}
