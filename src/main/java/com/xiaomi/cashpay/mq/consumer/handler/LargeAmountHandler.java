package com.xiaomi.cashpay.mq.consumer.handler;

import com.xiaomi.cashpay.mq.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


public class LargeAmountHandler implements MessageHandler<String, String> {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private Logger logger = LoggerFactory.getLogger(LargeAmountHandler.class);

    @Override
    public String handleMessage(final String message) {
        int curI = atomicInteger.incrementAndGet();
        logger.info("curI = {}, large amount message handler receive message={}", curI, message);
//        logger.info("curI = {}, current consumer thread = {}", curI, Thread.currentThread());
//        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
//            logger.info("---------curI = {}, current stack trace = {}", curI, stackTraceElement);
        return null;
    }
}