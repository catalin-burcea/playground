package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.SupplierUtils;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class CircuitBreakerDemoTest {

    @Test
    void decorateAFunctionalInterface() {
        // Given
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        // When I decorate my function
        CheckedFunction0<String> decoratedSupplier = CircuitBreaker
                .decorateCheckedSupplier(circuitBreaker, () -> "This can be any method which returns: 'Hello");

        // and chain an other function with map
        Try<String> result = Try.of(decoratedSupplier)
                .map(value -> value + " world'");

        // Then the Try Monad returns a Success<String>, if all functions ran successfully.
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo("This can be any method which returns: 'Hello world'");
    }

    @Test
    void recoverFromAnExceptionAfterCBRecordedItAsAFailure() {
        // Given
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        // When I decorate my function and invoke the decorated function
        CheckedFunction0<String> checkedSupplier =
                CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
                    throw new RuntimeException("BAM!");
                });

        Try<String> result = Try.of(checkedSupplier)
                .recover(throwable -> "Hello Recovery");

        // Then the function should be a success,
        // because the exception could be recovered
        assertThat(result.isSuccess()).isTrue();
        // and the result must match the result of the recovery function.
        assertThat(result.get()).isEqualTo("Hello Recovery");

    }

    @Test
    void recoverFromAnExceptionBeforeCBRecordsItAsAFailure() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        Supplier<String> supplier = () -> {
            throw new RuntimeException("BAM!");
        };

        Supplier<String> supplierWithRecovery = SupplierUtils
                .recover(supplier, (exception) -> "Hello Recovery");

        String result = circuitBreaker.executeSupplier(supplierWithRecovery);

        assertThat(result).isEqualTo("Hello Recovery");
    }

    @Test
    void recoverFromAnExceptionBeforeCBRecordsItAsAFailure2() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        Supplier<String> supplier = () -> "test";
        Supplier<String> supplierWithResultHandling = SupplierUtils.andThen(supplier, result -> {
            if (result.equals("test")) {
                throw new IllegalArgumentException();
            } else if (result.equals("test2")){
                throw new RuntimeException();
            }
            return result;
        }, (throwable) -> "recover from exception");

        String result = circuitBreaker.executeSupplier(supplierWithResultHandling);

        assertThat(result).isEqualTo("recover from exception");
    }
}
