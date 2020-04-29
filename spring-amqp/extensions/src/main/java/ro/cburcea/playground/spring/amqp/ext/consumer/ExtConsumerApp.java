package ro.cburcea.playground.spring.amqp.ext.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

@SpringBootApplication
public class ExtConsumerApp {

    private static final String EXTENSIONS_QUEUE = "extensions.queue";
    private static final String EXT_ALTERNATE_QUEUE = "ext.alternate.queue";

    public static void main(String[] args) {
        SpringApplication.run(ExtConsumerApp.class, args);
    }

    //priority = consumer priority
    @RabbitListener(queues = EXTENSIONS_QUEUE, priority = "5")
    public void consumer1(String in, Channel channel, Message message) {
        System.out.println("Consumer1: Message read from EXTENSIONS_QUEUE: " + in);
    }

    @RabbitListener(queues = EXTENSIONS_QUEUE, priority = "10", containerFactory = "prefetchContainerFactory")
    public void consumer2(String in, Channel channel, Message message) {
        System.out.println("Consumer2: Message read from EXTENSIONS_QUEUE: " + in);
    }

    @RabbitListener(queues = EXTENSIONS_QUEUE, priority = "10", ackMode = "MANUAL", containerFactory = "prefetchContainerFactory")
    public void consumer3(String in, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Consumer3: Message rejected from EXTENSIONS_QUEUE: " + in);
        channel.basicNack(tag, false, true);
    }

    @RabbitListener(queues = EXT_ALTERNATE_QUEUE)
    public void alternateConsumer(String in) {
        System.out.println("alternateConsumer: Message read from EXT_ALTERNATE_QUEUE: " + in);
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchContainerFactory(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setPrefetchCount(2);
        return factory;
    }

}