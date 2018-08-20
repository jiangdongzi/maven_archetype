import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2TestThreshold {
    public static void main(String[] args) {
//        System.out.println("hello jxj......");
        Logger logger = LogManager.getLogger(Log4j2TestThreshold.class.getName());
        logger.warn("hello jxj abc");
    }
}
