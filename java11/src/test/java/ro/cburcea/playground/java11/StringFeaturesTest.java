package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringFeaturesTest {

    @Test
    void testLinesStripAndIsBlankApis() {
        String multilineString = "Baeldung helps \n \n developers \n explore Java.";
        List<String> lines = multilineString.lines()
                .filter(line -> !line.isBlank())
                .map(String::strip)
                .collect(Collectors.toList());

        assertEquals(lines, Arrays.asList("Baeldung helps", "developers", "explore Java."));
    }

    @Test
    void testRepeat(){
        String output = "La ".repeat(2) + "Land";
        assertEquals(output, "La La Land");
    }

}
