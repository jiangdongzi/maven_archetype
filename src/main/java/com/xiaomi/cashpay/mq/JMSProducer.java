package com.xiaomi.cashpay.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URISyntaxException;

public class JMSProducer {
    public static void main(String[] args) throws URISyntaxException, Exception {
        Connection connection = null;
        try {
            // Producer
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://tj1-miui-cash-test-app00.kscn:6513");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("customerQueue");
            MessageProducer producer = session.createProducer(queue);
            String payload = "SomeTask";
            Message msg = session.createTextMessage(payload);
            System.out.println("Sending text '" + payload + "'");
            producer.send(msg);
            session.close();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}

