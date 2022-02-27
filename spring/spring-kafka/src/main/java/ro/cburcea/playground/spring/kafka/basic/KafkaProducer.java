package ro.cburcea.playground.spring.kafka.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static ro.cburcea.playground.spring.kafka.basic.KafkaTopicConfig.TOPIC1;
import static ro.cburcea.playground.spring.kafka.basic.KafkaTopicConfig.TOPIC2;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;

    @PostConstruct
    public void init() {
        sendMessage(TOPIC1, "hello from spring boot app: " + TOPIC1);
        sendMessage(TOPIC1, "hello from spring boot app2: " + TOPIC1);
        sendMessage(TOPIC1, "hello from spring boot app3: " + TOPIC1);
        sendMessage(TOPIC1, "hello from spring boot app4: " + TOPIC1);
        sendMessage(TOPIC1, "hello from spring boot app5: " + TOPIC1);
        sendGreetingMessage(TOPIC1, new Greeting("Hello", "Gigel"));
        sendGreetingMessage(TOPIC1, new Greeting("Hello", "World"));

        sendMessage(TOPIC2, "hello from spring boot app: " + TOPIC2);
        sendMessage(TOPIC2, "hello from spring boot app2: " + TOPIC2);
        sendMessage(TOPIC2, "hello from spring boot app3: " + TOPIC2);
        sendMessage(TOPIC2, "hello from spring boot app4: " + TOPIC2);
        sendMessage(TOPIC2, "hello from spring boot app5: " + TOPIC2);
    }

    public void sendMessage(String topic, String msg) {
        kafkaTemplate.send(topic, msg).addCallback(
                result -> LOGGER.info("Message sent to topic: {}", msg),
                ex -> LOGGER.error("Failed to send message", ex)
        );
    }

    public void sendGreetingMessage(String topic, Greeting msg) {
        greetingKafkaTemplate.send(topic, msg).addCallback(
                result -> LOGGER.info("Message sent to topic: {}", msg),
                ex -> LOGGER.error("Failed to send message", ex)
        );
    }
}
