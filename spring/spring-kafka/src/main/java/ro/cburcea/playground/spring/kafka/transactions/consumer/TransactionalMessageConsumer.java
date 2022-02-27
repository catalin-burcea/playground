package ro.cburcea.playground.spring.kafka.transactions.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.Duration;
import java.util.Properties;

import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;


@SpringBootApplication
public class TransactionalMessageConsumer {

    private static final String CONSUMER_GROUP_ID = "my-group-id3";
    private static final String OUTPUT_TOPIC = "output";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final Logger LOG = LoggerFactory.getLogger(TransactionalMessageConsumer.class);

    public static void main(String[] args) {
        SpringApplication.run(TransactionalMessageConsumer.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEventListener() {
        LOG.info("EXECUTING : eventListener");
        consumeFromOutput();
    }

    public void consumeFromOutput() {

        KafkaConsumer<String, String> consumer = createKafkaConsumer();
        LOG.info("starting consuming from output");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
        }
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(singleton(OUTPUT_TOPIC));
        return consumer;
    }
}