package ro.cburcea.playground.spring.amqp.routing.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableScheduling
public class RoutingProducer {

    private static final String DIRECT_EXCHANGE = "direct";
    private static final String PUB_SUB_Q1 = "pub-sub-q1";
    private static final String PUB_SUB_Q2 = "pub-sub-q2";

    private static final String[] keys = {"info", "warn", "error"};

    private AtomicInteger index = new AtomicInteger(0);
    private AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(RoutingProducer.class, args);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
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
    public Binding binding1a(DirectExchange directExchange, Queue q1) {
        return BindingBuilder.bind(q1).to(directExchange).with("info");
    }

    @Bean
    public Binding binding1b(DirectExchange directExchange, Queue q1) {
        return BindingBuilder.bind(q1).to(directExchange).with("warn");
    }

    @Bean
    public Binding binding2a(DirectExchange directExchange, Queue q2) {
        return BindingBuilder.bind(q2).to(directExchange).with("warn");
    }

    @Bean
    public Binding binding2b(DirectExchange directExchange, Queue q2) {
        return BindingBuilder.bind(q2).to(directExchange).with("error");
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder();
        if (this.index.incrementAndGet() == 3) {
            this.index.set(0);
        }
        String key = keys[this.index.get()];
        builder.append(key).append(" log ");
        builder.append(this.count.incrementAndGet());
        String message = builder.toString();

        template.convertAndSend(directExchange().getName(), key, message);
        System.out.println("Sent: " + message);
    }

}