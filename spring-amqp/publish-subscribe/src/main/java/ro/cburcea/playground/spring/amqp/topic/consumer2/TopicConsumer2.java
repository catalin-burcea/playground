package ro.cburcea.playground.spring.amqp.topic.consumer2;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopicConsumer2 {

    private static final String PUB_SUB_Q2 = "pub-sub-q2";

    public static void main(String[] args) {
        SpringApplication.run(TopicConsumer2.class, args);
    }

    @RabbitListener(queues = PUB_SUB_Q2)
    public void listener(String in, Channel channel, Message message) {
        System.out.println("Consumer2: Message read from PUB_SUB_Q2 : " + in);
    }

}