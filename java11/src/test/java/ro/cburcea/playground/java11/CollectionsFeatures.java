package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionsFeatures {

    @Test
    void testSet() {
        Set<String> strKeySet = Set.of("key1", "key2", "key3");
        assertTrue(strKeySet.containsAll(Arrays.asList("key1", "key2", "key3")));
    }

}
