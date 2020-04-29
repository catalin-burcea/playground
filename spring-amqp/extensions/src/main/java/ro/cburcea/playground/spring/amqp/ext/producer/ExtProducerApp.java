package ro.cburcea.playground.spring.amqp.ext.producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ExtProducerApp {

    private static final String EXTENSIONS_QUEUE = "extensions.queue";
    private static final String EXT_ALTERNATE_QUEUE = "ext.alternate.queue";
    private static final String MAIN_EXCHANGE = "main.exchange";
    private static final String ALTERNATE_EXCHANGE = "alternate.exchange";

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(ExtProducerApp.class, args);
    }

    @Bean
    public Queue extensionsQueue() {
        Map<String, Object> args = new HashMap<>();

        //set queue length: If both arguments are set then both will apply; whichever limit is hit first will be enforced.
        args.put("x-max-length", 3);
        args.put("x-max-length-bytes", 1048576);

        //set per queue TTL
        args.put("x-expires", 30000);

        //set priority; 10 recommended
        args.put("x-max-priority", 10);

        return new Queue(EXTENSIONS_QUEUE, false, false, false, args);
    }

    @Bean
    public Queue alternateQueue() {
        return QueueBuilder.nonDurable(EXT_ALTERNATE_QUEUE).build();
    }

    @Bean
    DirectExchange mainExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", ALTERNATE_EXCHANGE);
        return new DirectExchange(MAIN_EXCHANGE, false, false, args);
    }

    @Bean
    FanoutExchange alternateExchange() {
        return new FanoutExchange(ALTERNATE_EXCHANGE, false, false);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(extensionsQueue()).to(mainExchange()).with(EXTENSIONS_QUEUE);
    }

    @Bean
    Binding alternateBinding() {
        return BindingBuilder.bind(alternateQueue()).to(alternateExchange());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void send() {
        final String expiration = "20000";
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            template.convertAndSend(MAIN_EXCHANGE, EXTENSIONS_QUEUE, "msg" + i, m -> {
                m.getMessageProperties().setExpiration(expiration);
                m.getMessageProperties().setPriority(finalI);
                return m;
            });
        }
        System.out.println("Sent 10 messages to main exchange");

        template.convertAndSend(MAIN_EXCHANGE, "fake-routing-key", "alternate message");
        System.out.println("Sent one message to alternate exchange");
    }

}