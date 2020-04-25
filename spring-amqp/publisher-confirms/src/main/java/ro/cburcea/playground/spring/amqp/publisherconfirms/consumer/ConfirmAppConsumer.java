package ro.cburcea.playground.spring.amqp.publisherconfirms.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ConfirmAppConsumer {

    private static final String PUBLISHER_CONFIRMS_QUEUE = "publisher.confirms.queue";

    public static void main(String[] args) {
        SpringApplication.run(ConfirmAppConsumer.class, args);
    }

    @RabbitListener(queues = PUBLISHER_CONFIRMS_QUEUE)
    public void listener(String in, Channel channel, Message message) {
        System.out.println("Consumer: Message read from PUBLISHER_CONFIRMS_QUEUE : " + in);
    }

}