package ro.cburcea.playground.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

//            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String infoMessage = "info log message";
            String warnMessage = "warn log message";
            String errorMessage = "error log message";
            String errorMessage2 = "error log message2";

            channel.basicPublish(EXCHANGE_NAME, "info", null, infoMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "warn", null, warnMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "error", null, errorMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "error", null, errorMessage2.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + infoMessage + "'");
            System.out.println(" [x] Sent '" + warnMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage2 + "'");
        }
    }
} 