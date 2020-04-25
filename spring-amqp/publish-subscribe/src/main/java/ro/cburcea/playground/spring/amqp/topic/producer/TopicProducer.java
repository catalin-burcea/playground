package ro.cburcea.playground.spring.amqp.topic.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class TopicProducer {

    private static final String TOPIC_EXCHANGE = "topic";
    private static final String PUB_SUB_Q1 = "pub-sub-q1";
    private static final String PUB_SUB_Q2 = "pub-sub-q2";

    private static final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    private AtomicInteger index = new AtomicInteger(0);

    private AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(TopicProducer.class, args);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
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
    public Binding binding1a(TopicExchange topicExchange, Queue q1) {
        return BindingBuilder.bind(q1).to(topicExchange).with("*.orange.*");
    }

    @Bean
    public Binding binding1b(TopicExchange topicExchange, Queue q1) {
        return BindingBuilder.bind(q1).to(topicExchange).with("*.*.rabbit");
    }

    @Bean
    public Binding binding2a(TopicExchange topicExchange, Queue q2) {
        return BindingBuilder.bind(q2).to(topicExchange).with("lazy.#");
    }


    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (this.index.incrementAndGet() == keys.length) {
            this.index.set(0);
        }
        String key = keys[this.index.get()];
        builder.append(key).append(' ');
        builder.append(this.count.incrementAndGet());
        String message = builder.toString();

        template.convertAndSend(topicExchange().getName(), key, message);
        System.out.println("Sent: " + message);
    }

}