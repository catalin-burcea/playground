package ro.cburcea.playground.spring.kafka.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

import static ro.cburcea.playground.spring.kafka.basic.KafkaTopicConfig.TOPIC1;
import static ro.cburcea.playground.spring.kafka.basic.KafkaTopicConfig.TOPIC2;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = TOPIC1, groupId = "bar")
    public void listenGroupBar(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.info("listenGroupBar -> partition {}, Received Message: {} ", partition, message);

    }

    // consumers from "bar" groupId competes for messages from TOPIC2 (competing consumers pattern)
    @KafkaListener(topics = TOPIC2, groupId = "bar")
    public void competingConsumer1(String message) {
        LOGGER.info("competingConsumer1 -> Received Message: {} ", message);
    }

    @KafkaListener(topics = TOPIC2, groupId = "bar")
    public void competingConsumer2(String message) {
        LOGGER.info("competingConsumer2 -> Received Message: {} ", message);
    }

    @KafkaListener(topics = {TOPIC1, TOPIC2}, groupId = "foo", topicPartitions = {
            @TopicPartition(topic = TOPIC1, partitions = {"0"}),
            @TopicPartition(topic = TOPIC2, partitions = {"0", "1"})
    })
    public void listenFromMultipleTopics(String message,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.info("listenFromMultipleTopics -> Topic {}, Partition {}, Received Message: {}", topic, partition, message);
    }

    @KafkaListener(topics = {TOPIC1, TOPIC2}, groupId = "foo", topicPartitions = {
            @TopicPartition(topic = TOPIC1, partitions = {"1"}),
            @TopicPartition(topic = TOPIC2, partitions = {"2"})
    })
    public void listenFromMultipleTopics2(String message,
                                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.info("listenFromMultipleTopics2 -> Topic {}, Partition {}, Received Message: {} ", topic, partition, message);
    }

    @KafkaListener(topics = TOPIC1, groupId = "headers")
    public void listenWithHeaders(@Payload String message,
                                  @Headers Map<String, String> headers) {
        LOGGER.info("Received Message: {}", message);
        printHeaders(headers);
    }

    @KafkaListener(topics = TOPIC1, containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        LOGGER.info("listenWithFilter -> Received Message in filtered listener: {}", message);
    }

    private void printHeaders(Map<String, String> headers) {
        LOGGER.info("Received Headers: ");
        headers.entrySet().forEach(entry -> LOGGER.info("{}={}", entry.getKey(), entry.getValue()));
    }

}
