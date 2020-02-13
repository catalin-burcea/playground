package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.vavr.control.Try;

import java.util.function.Supplier;

public class MetricsDemo {

    public static void main(String[] args) {
        exportMetrics();
    }

    private static void exportMetrics() {
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.ofDefaults();

        CircuitBreaker foo = circuitBreakerRegistry
                .circuitBreaker("backendA");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(foo, () -> "hello");

        executeTasks(decoratedSupplier);

        TaggedCircuitBreakerMetrics
                .ofCircuitBreakerRegistry(circuitBreakerRegistry)
                .bindTo(meterRegistry);

        meterRegistry.forEachMeter((meter) -> System.out.println(meter.getId() + "; " + meter.measure()));
    }

    private static void executeTasks(Supplier<String> decoratedSupplier) {
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
        Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();
    }
}
