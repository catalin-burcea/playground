package ro.cburcea.playground.spring.amqp.routing.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoutingConsumers {

    private static final String PUB_SUB_Q1 = "pub-sub-q1";
    private static final String PUB_SUB_Q2 = "pub-sub-q2";

    public static void main(String[] args) {
        SpringApplication.run(RoutingConsumers.class, args);
    }

    @RabbitListener(queues = PUB_SUB_Q1)
    public void listener1(String in, Channel channel, Message message) {
        System.out.println("Consumer1: Message read from PUB_SUB_Q1 : " + in);
    }

    @RabbitListener(queues = PUB_SUB_Q2)
    public void listener2(String in, Channel channel, Message message) {
        System.out.println("Consumer2: Message read from PUB_SUB_Q2 : " + in);
    }

}