package ro.cburcea.playground.spring.amqp.pubsub.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
public class PubSubProducer {

    private static final String FANOUT_EXCHANGE = "fanout";
    private static final String PUB_SUB_Q1 = "pub-sub-q1";
    private static final String PUB_SUB_Q2 = "pub-sub-q2";

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(PubSubProducer.class, args);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue q1() {
        return new Queue(PUB_SUB_Q1);
    }

    @Bean
    public Queue q2() {
        return new Queue(PUB_SUB_Q2);
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, Queue q1) {
        return BindingBuilder.bind(q1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, Queue q2) {
        return BindingBuilder.bind(q2).to(fanout);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "message" + new Random().nextInt();

        //The routing key is ignored for fanout exchanges.
        template.convertAndSend(fanoutExchange().getName(), "", message);
        System.out.println("Sent: " + message);
    }

}