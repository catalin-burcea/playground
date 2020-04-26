package ro.cburcea.playground.spring.amqp.deadletter.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static ro.cburcea.playground.spring.amqp.deadletter.MessagesConsumer.HEADER_X_RETRIES_COUNT;
import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES_DLQ;

public class DLQCustomAmqpContainer {

    private static final Logger log = LoggerFactory.getLogger(DLQCustomAmqpContainer.class);
    public static final int MAX_RETRIES_COUNT = 3;
    private final RabbitTemplate rabbitTemplate;

    public DLQCustomAmqpContainer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryHeaders(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null)
            retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Discarding message");
            return;
        }
        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.convertAndSend(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }
}