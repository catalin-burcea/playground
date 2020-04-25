package ro.cburcea.playground.spring.amqp.competingconsumers.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageAppConsumer {

    private static final String WORK_QUEUE = "work.queue";

    public static void main(String[] args) {
        SpringApplication.run(MessageAppConsumer.class, args);
    }

    @RabbitListener(queues = WORK_QUEUE)
    public void consumer1(String message) {
        System.out.println("consumer1: Message read from WORK_QUEUE: " + message);
    }

    @RabbitListener(queues = WORK_QUEUE)
    public void consumer2(String message) {
        System.out.println("consumer2: Message read from WORK_QUEUE: " + message);
    }

}