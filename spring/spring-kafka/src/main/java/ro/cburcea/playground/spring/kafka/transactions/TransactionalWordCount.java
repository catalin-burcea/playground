package ro.cburcea.playground.spring.kafka.transactions;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Component
public class TransactionalWordCount {

    private static final String CONSUMER_GROUP_ID = "my-group-id2";
    private static final String OUTPUT_TOPIC = "output";
    private static final String INPUT_TOPIC = "input";

    private static final Logger LOG = LoggerFactory.getLogger(TransactionalWordCount.class);
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";


    public void process() {

        LOG.info("creating consumer");
        KafkaConsumer<String, String> consumer = createKafkaConsumer();
        LOG.info("creating producer");
        KafkaProducer<String, String> producer = createKafkaProducer();

        producer.initTransactions();
        LOG.info("producer initiated a transaction");

        try {

            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(ofSeconds(10));
                LOG.info("records count {}", records.count());
                LOG.info("consumer is counting the words");
                Map<String, Integer> wordCountMap = records.records(new TopicPartition(INPUT_TOPIC, 0))
                        .stream()
                        .flatMap(record -> Stream.of(record.value()
                                .split(" ")))
                        .map(word -> Tuple.of(word, 1))
                        .collect(Collectors.toMap(Tuple::getKey, Tuple::getValue, (v1, v2) -> v1 + v2));

                producer.beginTransaction();

                LOG.info("wordCountMap.size {} ", wordCountMap.size());
                LOG.info("producer is going to sleep");
                wordCountMap.forEach((key, value) -> {
                    LOG.info("key {}, value {} ", key, value);
                    producer.send(new ProducerRecord<>(OUTPUT_TOPIC, key, value.toString()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

                LOG.info("producer is computing the offsets");
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionedRecords = records.records(partition);
                    long offset = partitionedRecords.get(partitionedRecords.size() - 1)
                            .offset();

                    offsetsToCommit.put(partition, new OffsetAndMetadata(offset + 1));
                }

                LOG.info("producer is sending offsets");
                producer.sendOffsetsToTransaction(offsetsToCommit, CONSUMER_GROUP_ID);
                LOG.info("producer is committing");
                producer.commitTransaction();

            }

        } catch (KafkaException e) {

            producer.abortTransaction();

        }
    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(singleton(INPUT_TOPIC));
        return consumer;
    }

    private static KafkaProducer<String, String> createKafkaProducer() {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(TRANSACTIONAL_ID_CONFIG, "prod-1");
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new KafkaProducer<>(props);

    }

}