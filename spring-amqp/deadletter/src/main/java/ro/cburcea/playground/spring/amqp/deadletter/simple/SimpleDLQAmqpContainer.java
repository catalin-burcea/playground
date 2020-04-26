package ro.cburcea.playground.spring.amqp.deadletter.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES_DLQ;

public class SimpleDLQAmqpContainer {
    private static final Logger log = LoggerFactory.getLogger(SimpleDLQAmqpContainer.class);
    private final RabbitTemplate rabbitTemplate;

    public SimpleDLQAmqpContainer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRequeue(Message failedMessage) {
        log.info("Received failed message, sending back to original queue: {}", failedMessage);
        log.info("Received failed message, sending back to original queue: {}", failedMessage.getMessageProperties().getReceivedRoutingKey());
        rabbitTemplate.convertAndSend(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }
}