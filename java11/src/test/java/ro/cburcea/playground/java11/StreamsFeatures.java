package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamsFeatures {

    @Test
    void testPredicateNot() {
        List<String> sampleList = Arrays.asList("Java", "\n \n", "Kotlin", " ");
        List withoutBlanks = sampleList.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());

        assertEquals(withoutBlanks, Arrays.asList("Java", "Kotlin"));
    }

    @Test
    void testLocalVariableSyntaxForLambda() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String resultString = sampleList.stream()
                .map((@Nonnull var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));

        assertEquals(resultString, "JAVA, KOTLIN");
    }

    @Test
    void testOptionalStreams() {
        final List<Optional<String>> optionals = Arrays.asList(Optional.of("test1"), Optional.empty(), Optional.of("test2"));
        List<String> filteredList = optionals.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList("test1", "test2"),filteredList);
    }

    @Test
    void whenListContainsInteger_OrElseThrowReturnsInteger() {
        Integer firstEven = Stream.of(1,2,3,4)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow();

        assertEquals(Integer.valueOf(2), firstEven);
    }

}
