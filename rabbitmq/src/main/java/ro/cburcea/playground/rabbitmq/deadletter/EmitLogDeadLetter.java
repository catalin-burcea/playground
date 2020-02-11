package ro.cburcea.playground.rabbitmq.deadletter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

import static ro.cburcea.playground.rabbitmq.deadletter.DeadLetterConstants.DEAD_LETTER_EXCHANGE_NAME;
import static ro.cburcea.playground.rabbitmq.deadletter.DeadLetterConstants.EXCHANGE_NAME;

public class EmitLogDeadLetter {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            channel.exchangeDeclare(DEAD_LETTER_EXCHANGE_NAME, "topic");

            String infoMessage = "info log message";
            String warnMessage = "warn log message";
            String errorMessage = "error log message";
            String errorMessage2 = "error log message2";

            channel.basicPublish(EXCHANGE_NAME, "app1.info", null, infoMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "app1.warn", null, warnMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "app2.error", null, errorMessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "app3.error", null, errorMessage2.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] Sent '" + infoMessage + "'");
            System.out.println(" [x] Sent '" + warnMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage + "'");
            System.out.println(" [x] Sent '" + errorMessage2 + "'");
        }
    }
} 