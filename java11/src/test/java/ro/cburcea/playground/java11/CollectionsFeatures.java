package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionsFeatures {

    @Test
    void testSetOfAndListOf() {
        Set<String> strKeySet = Set.of("key1", "key2", "key3");
        assertTrue(strKeySet.containsAll(Arrays.asList("key1", "key2", "key3")));

        List<String> strKeyList = List.of("key1", "key2", "key3");
        assertTrue(strKeyList.containsAll(Arrays.asList("key1", "key2", "key3")));
    }

    @Test
    void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> copyList = List.copyOf(List.of(1, 2, 3, 4));

        assertThrows(UnsupportedOperationException.class, () -> copyList.add(4));
    }

    @Test
    void whenModifyToUnmodifiableList_thenThrowsException() {
        List<Integer> evenList = Stream.of(1, 2, 3, 4)
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toUnmodifiableList());

        assertThrows(UnsupportedOperationException.class, () -> evenList.add(4));

    }

}
