package com.xiaomi.cashpay.mq.consumer.handler;

import com.xiaomi.cashpay.mq.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LargeAmountHandler implements MessageHandler<String, String> {

    private Logger logger = LoggerFactory.getLogger(LargeAmountHandler.class);

    @Override
    public String handleMessage(final String message) {
        logger.info("large amount message handler receive message={}", message);
        System.out.println("message = {}" + message);
        return null;
    }

}