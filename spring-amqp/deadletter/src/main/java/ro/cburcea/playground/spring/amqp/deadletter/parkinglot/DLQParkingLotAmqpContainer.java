package ro.cburcea.playground.spring.amqp.deadletter.parkinglot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static ro.cburcea.playground.spring.amqp.deadletter.MessagesConsumer.HEADER_X_RETRIES_COUNT;
import static ro.cburcea.playground.spring.amqp.deadletter.parkinglot.DLXParkingLotAmqpConfiguration.EXCHANGE_PARKING_LOT;
import static ro.cburcea.playground.spring.amqp.deadletter.parkinglot.DLXParkingLotAmqpConfiguration.QUEUE_PARKING_LOT;
import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static ro.cburcea.playground.spring.amqp.deadletter.simple.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES_DLQ;

public class DLQParkingLotAmqpContainer {
    private static final Logger log = LoggerFactory.getLogger(DLQParkingLotAmqpContainer.class);
    private final RabbitTemplate rabbitTemplate;
    public static final int MAX_RETRIES_COUNT = 3;

    public DLQParkingLotAmqpContainer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryWithParkingLot(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null)
            retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            rabbitTemplate.send(EXCHANGE_PARKING_LOT, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    @RabbitListener(queues = QUEUE_PARKING_LOT)
    public void processParkingLotQueue(Message failedMessage) {
        log.info("Received message in parking lot queue {}", failedMessage);
    }
}