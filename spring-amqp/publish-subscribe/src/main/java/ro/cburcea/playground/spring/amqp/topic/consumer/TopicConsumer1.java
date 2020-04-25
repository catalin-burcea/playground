package ro.cburcea.playground.spring.amqp.topic.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopicConsumer1 {

    private static final String PUB_SUB_Q1 = "pub-sub-q1";

    public static void main(String[] args) {
        SpringApplication.run(TopicConsumer1.class, args);
    }

    @RabbitListener(queues = PUB_SUB_Q1)
    public void listener(String in, Channel channel, Message message) {
        System.out.println("Consumer1: Message read from PUB_SUB_Q1 : " + in);
    }

}