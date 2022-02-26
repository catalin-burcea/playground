package ro.cburcea.playground.spring.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC1 = "topic1";
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";

    @Value(value = "${kafka.bootstrap-address}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(TOPIC1, 2, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(TOPIC2).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic topicWithConfigs() {
        Map<String, String> newTopicConfig = new HashMap<>();

        newTopicConfig.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);

        // With retention period properties in place, messages have a TTL (time to live). Upon expiry, messages are marked for deletion, thereby freeing up the disk space.
        // This configuration controls the maximum time we will retain a log before we will discard old log segments to free up space if we are using the "delete" retention policy.
        newTopicConfig.put(TopicConfig.RETENTION_MS_CONFIG, "604800000");

        // This configuration controls the maximum size a partition (which consists of log segments) can grow to before we will discard old log segments to free up space if we are using the "delete" retention policy.
        // By default there is no size limit only a time limit. Since this limit is enforced at the partition level, multiply it by the number of partitions to compute the topic retention in bytes.
        newTopicConfig.put(TopicConfig.RETENTION_BYTES_CONFIG, "-1");

        // This configuration controls the segment file size for the log. Retention and cleaning is always done a file at a time so a larger segment size means fewer files but less granular control over retention.
        newTopicConfig.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");

        newTopicConfig.put(TopicConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        return new NewTopic(TOPIC3, 1, (short) 1).configs(newTopicConfig);
    }
}