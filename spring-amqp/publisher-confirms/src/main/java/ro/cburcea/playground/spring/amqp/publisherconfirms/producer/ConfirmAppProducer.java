package ro.cburcea.playground.spring.amqp.publisherconfirms.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ConfirmAppProducer {

    private static final String PUBLISHER_CONFIRMS_QUEUE = "publisher.confirms.queue";

    @Autowired
    private RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(ConfirmAppProducer.class, args);
    }

    @Bean
    public SimpleMessageListenerContainer container(CachingConnectionFactory rabbitConnectionFactory) {
        rabbitConnectionFactory.setChannelCacheSize(15);
        rabbitConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        rabbitConnectionFactory.setPublisherReturns(true);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory);

        return container;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    @Bean
    public Queue myQueue() {
        return new Queue(PUBLISHER_CONFIRMS_QUEUE, false);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message1 = "message1";
        String message2 = "message2";
        CorrelationData cd1 = new CorrelationData(message1);
        CorrelationData cd2 = new CorrelationData(message2);

        template.setMandatory(true);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message sent successfully: Message sent to RabbitMQ exchange");
            } else {
                System.out.println("Message sending failed: Message not sent to RabbitMQ exchange");
            }
        });
        template.setReturnCallback((msg, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("Message returned: No corresponding queue bound to switch");
        });

        template.convertAndSend(directExchange().getName(), PUBLISHER_CONFIRMS_QUEUE, message1, cd1);
        template.convertAndSend(directExchange().getName(), "fakeQueue", message1, cd1);
        template.convertAndSend("fakeExchange", "test", message2, cd2);
    }

}