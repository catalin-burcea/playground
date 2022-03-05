package ro.cburcea.playground.spring.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class WordCountProcessor {

    public static final String INPUT_TOPIC = "streams-wordcount-input";
    public static final String OUTPUT_TOPIC = "streams-wordcount-output";
    public static final String WORD_COUNTS = "wordcounts";

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        KTable<String, Long> wordCounts = messageStream
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .flatMapValues(value -> Arrays.asList(pattern.split(value)))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as(WORD_COUNTS));

        wordCounts.toStream()
                .foreach((word, count) -> System.out.println("word: " + word + " -> " + count));

        wordCounts.toStream().to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    }
}