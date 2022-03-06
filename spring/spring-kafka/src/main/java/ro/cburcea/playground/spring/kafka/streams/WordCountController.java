package ro.cburcea.playground.spring.kafka.streams;


import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WordCountController {

    private final StreamsBuilderFactoryBean factoryBean;

    public WordCountController(StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    @GetMapping("/count/{word}")
    public Long getWordCount(@PathVariable String word) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts =
                kafkaStreams.store(WordCountProcessor.WORD_COUNTS_STORE, QueryableStoreTypes.keyValueStore());
        return counts.get(word);
    }

    @GetMapping("/wordcounts")
    public Map getWordCounts() {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts =
                kafkaStreams.store(WordCountProcessor.WORD_COUNTS_STORE, QueryableStoreTypes.keyValueStore());

        Map<String, Long> result = new HashMap<>();
        KeyValueIterator<String, Long> range = counts.all();
        while (range.hasNext()) {
            KeyValue<String, Long> next = range.next();
            result.put(next.key, next.value);
        }
        return result;
    }

    @GetMapping("/wordCountPerSentence")
    public Map wordCountPerSentence() {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts =
                kafkaStreams.store(WordCountProcessor.SENTENCE_LENGTHS_STORE, QueryableStoreTypes.keyValueStore());

        Map<String, Long> result = new HashMap<>();
        KeyValueIterator<String, Long> range = counts.all();
        while (range.hasNext()) {
            KeyValue<String, Long> next = range.next();
            result.put(next.key, next.value);
        }
        return result;
    }
}
