package ro.cburcea.playground.spring.amqp.competingconsumers.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class MessageAppProducer {

    private static final String WORK_QUEUE = "work.queue";

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(MessageAppProducer.class, args);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(WORK_QUEUE, false);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "message" + new Random().nextInt();
        template.convertAndSend(WORK_QUEUE, message); // default exchange
        System.out.println("Sent: " + message);
    }

}