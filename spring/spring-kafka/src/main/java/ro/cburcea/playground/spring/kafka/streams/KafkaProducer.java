package ro.cburcea.playground.spring.kafka.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static ro.cburcea.playground.spring.kafka.streams.WordCountProcessor.INPUT_TOPIC;
import static ro.cburcea.playground.spring.kafka.streams.WordCountProcessor.INPUT_TOPIC2;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        sendMessage(INPUT_TOPIC, "hello world");
        sendMessage(INPUT_TOPIC, "how are you");
        sendMessage(INPUT_TOPIC2, "hello world");
        sendMessage(INPUT_TOPIC2, "hello John");
        sendMessage(INPUT_TOPIC2, "how are you");
    }

    public void sendMessage(String topic, String msg) {
        kafkaTemplate.send(topic, msg).addCallback(
                result -> LOGGER.info("Message sent to topic: {}", msg),
                ex -> LOGGER.error("Failed to send message", ex)
        );
    }

}
