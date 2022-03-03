package ro.cburcea.playground.spring.kafka.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static ro.cburcea.playground.spring.kafka.streams.WordCountProcessor.OUTPUT_TOPIC;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = OUTPUT_TOPIC, groupId = "word-count")
    public void wordCount(String message) {
        LOGGER.info("Received Message: {} ", message);
    }

}
