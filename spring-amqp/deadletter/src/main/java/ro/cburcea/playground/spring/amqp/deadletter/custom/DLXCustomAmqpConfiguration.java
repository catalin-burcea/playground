package ro.cburcea.playground.spring.amqp.deadletter.custom;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.*;


@Configuration
@ConditionalOnProperty(
        value = "amqp.configuration.current",
        havingValue = "dlx-custom")
public class DLXCustomAmqpConfiguration {

    public static final String DLX_EXCHANGE_MESSAGES = QUEUE_MESSAGES + ".dlx";

    @Bean
    Queue messagesQueue() {
        return QueueBuilder.nonDurable(QUEUE_MESSAGES)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_EXCHANGE_MESSAGES);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.nonDurable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
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