package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

}
