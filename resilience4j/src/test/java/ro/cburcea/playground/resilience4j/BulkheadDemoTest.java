package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BulkheadDemoTest {

    @Test
    void decorateAFunctionalInterface() {
        // Given
        Bulkhead bulkhead = Bulkhead.ofDefaults("name");

        // When I decorate my function
        CheckedFunction0<String> decoratedSupplier = Bulkhead
                .decorateCheckedSupplier(bulkhead, () -> "This can be any method which returns: 'Hello");

        // and chain an other function with map
        Try<String> result = Try.of(decoratedSupplier)
                .map(value -> value + " world'");

        // Then the Try Monad returns a Success<String>, if all functions ran successfully.
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo("This can be any method which returns: 'Hello world'");
        assertThat(bulkhead.getMetrics().getAvailableConcurrentCalls()).isEqualTo(25);
    }
}
