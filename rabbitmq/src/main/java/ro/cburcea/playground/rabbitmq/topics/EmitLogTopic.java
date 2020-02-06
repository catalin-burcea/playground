package ro.cburcea.playground.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String infoMessage = "info log message";
            String warnMessage = "warn log message";
            String errorMessage = "error log message";
            String errorMessage2 = "error log message2";

            channel.basicPublish(EXCHANGE_NAME, "app1.info", null, infoMessage.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, "app1.warn", null, warnMessage.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, "app2.error", null, errorMessage.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, "app3.error", null, errorMessage2.getBytes("UTF-8"));

            System.out.println(" [x] Sent '" + infoMessage + "'");
            System.out.println(" [x] Sent '" + warnMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage2 + "'");
        }
    }
} 