package com.xiaomi.cashpay.mq.consumer.handler;

import com.xiaomi.cashpay.mq.Constants;
import com.xiaomi.cashpay.mq.ProducerBuilder;
import com.xiaomi.cashpay.mq.listener.XMessageListenerContainer;
import com.xiaomi.cashpay.mq.processor.XConnectionPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * Created by song.yang on 18-12-12 下午4:06
 * <p>
 * e-mail:yangsong5@xiaomi.com
 */
public class LargeAmountHandlerBuilder {
    private Logger logger = LoggerFactory.getLogger(LargeAmountHandlerBuilder.class);

    //web大额动账mq并发数配置(zk)

    private static String CMF_CASHIER_UPOP_TOKEN_EXPIRED_QUEUE = "cmf.cashier.upop.token.expired.queue";

    private static final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private SimpleMessageConverter commonMessageConverter;
    private ConnectionFactory commonConnectionFactory;
    private boolean commonSessionTransacted = true;
    private ErrorHandler commonErrorHandler;
    private int commonRecoveryInterval;
    private XConnectionPostProcessor commonConnectionPostProcessor;

    private ThreadPoolTaskExecutor largeAmountTaskExecutor;
    private LargeAmountHandler largeAmountHandler;
    private ProducerBuilder producerBuilder;

    private XMessageListenerContainer largeAmountMessageListenerContainer = null;
    private String largeAmountConcurrency = "2-4";

    public void initialize() throws InterruptedException {
        System.out.println(".............................................spring thread = ." + Thread.currentThread());

        System.out.println("init large amount handler");

        largeAmountTaskExecutor = new ThreadPoolTaskExecutor();
        largeAmountTaskExecutor.setCorePoolSize(5);
        largeAmountTaskExecutor.setMaxPoolSize(10);
        largeAmountTaskExecutor.setQueueCapacity(2000);
        largeAmountTaskExecutor.setKeepAliveSeconds(60);
        largeAmountTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        largeAmountTaskExecutor.initialize();
        buildLargeAmountMessageListenerContainer();
        logger.info("finish build refund message listener container");
        Runtime.getRuntime().traceMethodCalls(true);
        long start = System.currentTimeMillis();
        logger.info("start time = {}", start);
        largeAmountMessageListenerContainer.start();
        long end = System.currentTimeMillis();
        logger.info("end time = {}", end);
        logger.info("end - start = {}", end - start);
        Constants.t = new Thread() {
            public void run() {
                logger.info("refund message listener container will be start after 60s");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                if (largeAmountMessageListenerContainer != null) {
                    System.out.println("start consume");
//                    largeAmountMessageListenerContainer.start();
                }
            }
        };

        Constants.t.start();


        Thread thread = new Thread() {
            public void run() {
                System.out.println("start produce");
                try {
                    Thread.sleep(20000);
                    while (true) {
                        System.out.println("send msg start");
                        producerBuilder.sendMessage("hello");
                        Thread.sleep(2000);
                        System.out.println("end msg send");
                    }
                } catch (Exception e) {
                    System.out.println("caught err");
                    System.out.println(e.toString());
                }
            }
        };

        thread.start();

        Constants.t1 = thread;
    }

    private void buildLargeAmountMessageListenerContainer() {
            MessageListenerAdapter largeAmountMessageListener = new MessageListenerAdapter(largeAmountHandler);
            largeAmountMessageListener.setMessageConverter(commonMessageConverter);

            largeAmountMessageListenerContainer = new XMessageListenerContainer();
            largeAmountMessageListenerContainer.setConnectionFactory(commonConnectionFactory);
            largeAmountMessageListenerContainer.setSessionTransacted(commonSessionTransacted);
            largeAmountMessageListenerContainer.setErrorHandler(commonErrorHandler);
            largeAmountMessageListenerContainer.setRecoveryInterval(commonRecoveryInterval);
            largeAmountMessageListenerContainer.setConnectionPostProcessor(commonConnectionPostProcessor);
            largeAmountMessageListenerContainer.setTaskExecutor(largeAmountTaskExecutor);
            largeAmountMessageListenerContainer.setConcurrency(largeAmountConcurrency);
            largeAmountMessageListenerContainer.setDestinationName(CMF_CASHIER_UPOP_TOKEN_EXPIRED_QUEUE);
            largeAmountMessageListenerContainer.setMessageListener(largeAmountMessageListener);
            largeAmountMessageListenerContainer.initialize();
    }

    public SimpleMessageConverter getCommonMessageConverter() {
        return commonMessageConverter;
    }

    public void setCommonMessageConverter(SimpleMessageConverter commonMessageConverter) {
        this.commonMessageConverter = commonMessageConverter;
    }

    public ConnectionFactory getCommonConnectionFactory() {
        return commonConnectionFactory;
    }

    public void setCommonConnectionFactory(ConnectionFactory commonConnectionFactory) {
        this.commonConnectionFactory = commonConnectionFactory;
    }

    public boolean isCommonSessionTransacted() {
        return commonSessionTransacted;
    }

    public void setCommonSessionTransacted(boolean commonSessionTransacted) {
        this.commonSessionTransacted = commonSessionTransacted;
    }

    public ErrorHandler getCommonErrorHandler() {
        return commonErrorHandler;
    }

    public void setCommonErrorHandler(ErrorHandler commonErrorHandler) {
        this.commonErrorHandler = commonErrorHandler;
    }

    public int getCommonRecoveryInterval() {
        return commonRecoveryInterval;
    }

    public void setCommonRecoveryInterval(int commonRecoveryInterval) {
        this.commonRecoveryInterval = commonRecoveryInterval;
    }

    public XConnectionPostProcessor getCommonConnectionPostProcessor() {
        return commonConnectionPostProcessor;
    }

    public void setCommonConnectionPostProcessor(XConnectionPostProcessor commonConnectionPostProcessor) {
        this.commonConnectionPostProcessor = commonConnectionPostProcessor;
    }

    public ThreadPoolTaskExecutor getLargeAmountTaskExecutor() {
        return largeAmountTaskExecutor;
    }

    public void setLargeAmountTaskExecutor(ThreadPoolTaskExecutor largeAmountTaskExecutor) {
        this.largeAmountTaskExecutor = largeAmountTaskExecutor;
    }

    public LargeAmountHandler getLargeAmountHandler() {
        return largeAmountHandler;
    }

    public void setLargeAmountHandler(LargeAmountHandler largeAmountHandler) {
        this.largeAmountHandler = largeAmountHandler;
    }

    public ProducerBuilder getProducerBuilder() {
        return producerBuilder;
    }

    public void setProducerBuilder(ProducerBuilder producerBuilder) {
        this.producerBuilder = producerBuilder;
    }
}