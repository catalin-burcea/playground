package ro.cburcea.playground.spring.amqp.deadletter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.cburcea.playground.spring.amqp.deadletter.custom.DLQCustomAmqpContainer;
import ro.cburcea.playground.spring.amqp.deadletter.parkinglot.DLQParkingLotAmqpContainer;
import ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration;
import ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpContainer;

@Configuration
public class MessagesConsumer {
    public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    private static final Logger log = LoggerFactory.getLogger(MessagesConsumer.class);

    @Bean
    @ConditionalOnProperty(
            value = "amqp.configuration.current",
            havingValue = "simple-dlq")
    public SimpleDLQAmqpContainer simpleAmqpContainer(RabbitTemplate rabbitTemplate) {
        return new SimpleDLQAmqpContainer(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(
            value = "amqp.configuration.current",
            havingValue = "dlx-custom")
    public DLQCustomAmqpContainer dlqAmqpContainer(RabbitTemplate rabbitTemplate) {
        return new DLQCustomAmqpContainer(rabbitTemplate);
    }

    @Bean
    @ConditionalOnProperty(
            value = "amqp.configuration.current",
            havingValue = "parking-lot-dlx")
    public DLQParkingLotAmqpContainer parkingLotDLQAmqpContainer(RabbitTemplate rabbitTemplate) {
        return new DLQParkingLotAmqpContainer(rabbitTemplate);
    }

    @RabbitListener(queues = SimpleDLQAmqpConfiguration.QUEUE_MESSAGES)
    public void receiveMessage(Message message) {
        log.info("Received message: {}", message);
        throw new AmqpRejectAndDontRequeueException("reject and don't requeue exception");
    }
}