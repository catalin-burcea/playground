package ro.cburcea.playground.spring.amqp.deadletter.simple;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "amqp.configuration.current",
        havingValue = "simple-dlq")
public class SimpleDLQAmqpConfiguration {

    public static final String QUEUE_MESSAGES = "messages-queue";
    public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES + ".dlq";
    public static final String EXCHANGE_MESSAGES = "messages-exchange";

    @Bean
    Queue messagesQueue() {
        return QueueBuilder.nonDurable(QUEUE_MESSAGES)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.nonDurable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    DirectExchange messagesExchange() {
        return new DirectExchange(EXCHANGE_MESSAGES);
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(QUEUE_MESSAGES);
    }
}