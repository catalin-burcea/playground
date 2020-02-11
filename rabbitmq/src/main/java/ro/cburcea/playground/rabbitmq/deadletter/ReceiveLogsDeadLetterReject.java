package ro.cburcea.playground.rabbitmq.deadletter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static ro.cburcea.playground.rabbitmq.deadletter.DeadLetterConstants.DEAD_LETTER_EXCHANGE_NAME;
import static ro.cburcea.playground.rabbitmq.deadletter.DeadLetterConstants.EXCHANGE_NAME;

public class ReceiveLogsDeadLetterReject {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME);

        String queueName = "reject_queue";
        channel.queueDeclare(queueName, false, false, false, args);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.warn", args);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.error", args);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Rejected: '" + message + "'");

            channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
        });
    }
}