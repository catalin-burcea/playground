package ro.cburcea.playground.spring.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class WordCountProcessor {

    public static final String INPUT_TOPIC = "streams-wordcount-input";
    public static final String INPUT_TOPIC2 = "streams-sentence-input";
    public static final String OUTPUT_TOPIC = "streams-wordcount-output";
    public static final String WORD_COUNTS_STORE = "wordCounts";
    public static final String SENTENCE_LENGTHS_STORE = "sentenceLengths";

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        KStream<String, String> messageStream1 = streamsBuilder
                .stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> messageStream2 = streamsBuilder
                .stream(INPUT_TOPIC2, Consumed.with(Serdes.String(), Serdes.String()));

        KTable<String, Long> wordCounts = messageStream1
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .flatMapValues(value -> Arrays.asList(pattern.split(value)))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as(WORD_COUNTS_STORE));

        KTable<String, Long> sentenceLength = messageStream2
                .peek((key, value) -> System.out.println(key + "  " + value))
                .groupBy((key, sentence) -> pattern.split(sentence)[0], Grouped.with(Serdes.String(), Serdes.String()))
                .aggregate(() -> 0L, (key, newValue, aggValue) ->
                                aggValue + newValue.length(),
                        Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(SENTENCE_LENGTHS_STORE)
                                .withKeySerde(Serdes.String()).withValueSerde(Serdes.Long()));

        wordCounts.toStream()
                .foreach((word, count) -> System.out.println("word: " + word + " -> " + count));

        sentenceLength.toStream()
                .foreach((sentenceKey, length) -> System.out.println("sentenceKey: " + sentenceKey + " -> " + length));

        wordCounts.toStream().to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    }
}