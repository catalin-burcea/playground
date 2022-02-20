package ro.cburcea.playground.spring.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static ro.cburcea.playground.spring.kafka.KafkaTopicConfig.TOPIC1;
import static ro.cburcea.playground.spring.kafka.KafkaTopicConfig.TOPIC2;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = TOPIC1, groupId = "bar")
    public void listenGroupBar(String message) {
        LOGGER.info("listenGroupBar -> Received Message: " + message);
    }

    @KafkaListener(topics = TOPIC2, groupId = "bar")
    public void listenGroupBar2(String message) {
        LOGGER.info("listenGroupBar2 -> Received Message: " + message);
    }

    @KafkaListener(topics = {TOPIC1, TOPIC2}, groupId = "foo")
    public void listenFromMultipleTopics(String message) {
        LOGGER.info("listenFromMultipleTopics -> Received Message: " + message);
    }
}
