package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;
import ro.cburcea.playground.java11.utils.Outer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NestBasedAccessControlTest {

    @Test
    void testNestBasedAccessControl() {
        Set<String> nestMembers = Arrays.stream(Outer.Inner.class.getNestMembers())
                .map(Class::getName)
                .collect(Collectors.toSet());

        assertEquals(Outer.class.getNestHost().getName(), "ro.cburcea.playground.java11.utils.Outer");
        assertEquals(Outer.Inner.class.getNestHost().getName(), "ro.cburcea.playground.java11.utils.Outer");
        assertTrue(Outer.Inner.class.isNestmateOf(Outer.class));
        assertEquals(nestMembers.size(), 2);
        assertTrue(nestMembers.contains("ro.cburcea.playground.java11.utils.Outer"));
        assertTrue(nestMembers.contains("ro.cburcea.playground.java11.utils.Outer$Inner"));
    }
}
