package com.xiaomi.cashpay.mq.consumer.handler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xiaomi.cashpay.mq.Constants;
import com.xiaomi.cashpay.mq.ProducerBuilder;
import com.xiaomi.cashpay.mq.listener.XMessageListenerContainer;
import com.xiaomi.cashpay.mq.processor.XConnectionPostProcessor;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.lang.reflect.WildcardType;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by song.yang on 18-12-12 下午4:06
 * <p>
 * e-mail:yangsong5@xiaomi.com
 */
public class LargeAmountHandlerBuilder {
    private static Logger logger = LoggerFactory.getLogger(LargeAmountHandlerBuilder.class);

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

    private ExecutorService singleWorker;
    public static Thread[] singleThreads = new Thread[5];
    private AtomicInteger threadIdx = new AtomicInteger();
    public void initialize() throws InterruptedException {
        System.out.println(".............................................spring thread = ." + Thread.currentThread());

        System.out.println("init large amount handler");
//        singleWorker = Executors.newFixedThreadPool(3);
//        logger.info("single thread = {}", singleWorker);

//        singleWorker.execute(new Runnable() {
//            @Override
//            public void run() {
//                int curIdx = threadIdx.incrementAndGet();
//                singleThreads[curIdx] = Thread.currentThread();
//            }
//        });

        Thread.sleep(1000);

//        logger.info("single thread = {}", singleWorker);
        largeAmountTaskExecutor = new ThreadPoolTaskExecutor();
        largeAmountTaskExecutor.setCorePoolSize(7);
        largeAmountTaskExecutor.setMaxPoolSize(11);
        largeAmountTaskExecutor.setQueueCapacity(2000);
        largeAmountTaskExecutor.setKeepAliveSeconds(60);
        largeAmountTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        largeAmountTaskExecutor.initialize();
        buildLargeAmountMessageListenerContainer();
        logger.info("finish build refund message listener container");
        Runtime.getRuntime().traceMethodCalls(true);
        long start = System.currentTimeMillis();
        logger.info("start time = {}", start);
        logger.info("cfg_of_container = {}", largeAmountMessageListenerContainer);
        Constants.toBeStart = largeAmountMessageListenerContainer;
        long end = System.currentTimeMillis();
        logger.info("end time = {}", end);
        logger.info("end - start = {}", end - start);


//        Constants.t = new Thread() {
//            public void run() {
//                logger.info("refund message listener container will be start after 60s");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                }
//                if (largeAmountMessageListenerContainer != null) {
//                    System.out.println("start consume");
////                    largeAmountMessageListenerContainer.start();
//                }
//            }
//        };
//
//        Constants.t.start();




        Thread thread = new Thread() {
            public void run() {
                System.out.println("start produce");
                try {
                    Thread.sleep(2000);
                    while (true) {
                        System.out.println("send msg start");
                        producerBuilder.sendMessage(buildMsg());
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


        for (int i = 0; i < 3; i++) {
//            new Thread(new StackTask(singleThreads[i]));
        }
    }
//l,"content":{"memberId":"119344101","cardNo":"622908323008791618","signNo":"5713334752288176",
// "fundChannelCode":"UPOP30110","instOrderNo":"7510019091652196937"},"referenceId":null}
    private String buildMsg() {
        JSONObject content = new JSONObject();
        try {
            content.put("memberId", "135438936");
            content.put("cardNo", "6227002470170278192");
            content.put("signNo", "6227002470170278192");
            content.put("fundChannelCode", "UPOP30110");
            content.put("instOrderNo", "135438936");
            JSONObject res = new JSONObject();
            res.put("content", content);
            return res.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void printContainerCfg() {
        logger.info("listener = {}", largeAmountMessageListenerContainer.getMessageListener());
    }

    private static class StackTask implements Runnable {
        Thread singleThread;

        public StackTask(Thread singleThread) {
            this.singleThread = singleThread;
        }

        public Thread getSingleThread() {
            return singleThread;
        }

        public void setSingleThread(Thread singleThread) {
            this.singleThread = singleThread;
        }

        @Override
        public void run() {
            long c = 0;
            while (true) {
                while (singleThreads[0].getState() != Thread.State.WAITING)
                {
                    c++;
                    for (StackTraceElement stackTraceElement : singleThread.getStackTrace()) {
                        logger.info("c = {}, curStack = {}", c, stackTraceElement);
                    }
                }
            }
        }
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
//            largeAmountMessageListenerContainer.setReceiveTimeout(-1);
//            largeAmountMessageListenerContainer.setTransactionTimeout(-1);
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