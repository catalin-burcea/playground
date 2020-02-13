package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.consumer.CircularEventConsumer;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class CircuitBreakerDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        executeADecoratedFunctionalInterface();
    }

    private static void createCircuitBreakerWithRegistry() {
        // Create a custom configuration for a CircuitBreaker
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(IndexOutOfBoundsException.class, NullPointerException.class)
                .build();

        // Create a CircuitBreakerRegistry with a custom global configuration
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        // Get or create a CircuitBreaker from the CircuitBreakerRegistry
        // with the global default configuration
        CircuitBreaker circuitBreakerWithDefaultConfig =
                circuitBreakerRegistry.circuitBreaker("name1");

        // Get or create a CircuitBreaker from the CircuitBreakerRegistry
        // with a custom configuration
        CircuitBreaker circuitBreakerWithCustomConfig = circuitBreakerRegistry
                .circuitBreaker("name2", circuitBreakerConfig);
    }

    private static void decorateAFunctionalInterface() {
        CircuitBreaker circuitBreaker = CircuitBreaker
                .ofDefaults("testName");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> {throw new RuntimeException();});

        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery")
                .get();

        logger.info(result);
    }

    private static void executeADecoratedFunctionalInterface() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        String result = circuitBreaker.executeSupplier(() -> "do something");

        logger.info(result);
    }

    private static void overwriteConfiguration(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig defaultConfig = circuitBreakerRegistry.getDefaultConfig();

        CircuitBreakerConfig overwrittenConfig = CircuitBreakerConfig
                .from(defaultConfig)
                .waitDurationInOpenState(Duration.ofSeconds(20))
                .build();
    }

    private static void createCircuitBreakerWithoutRegistry() {
        // Create a custom configuration for a CircuitBreaker
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .recordExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(IndexOutOfBoundsException.class, NullPointerException.class)
                .build();

        CircuitBreaker customCircuitBreaker = CircuitBreaker
                .of("testName", circuitBreakerConfig);
    }

    private static void consumeEmittedRegistryEvents() {
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        circuitBreakerRegistry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    CircuitBreaker addedCircuitBreaker = entryAddedEvent.getAddedEntry();
                    logger.info(String.format("CircuitBreaker %s added", addedCircuitBreaker.getName()));
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    CircuitBreaker removedCircuitBreaker = entryRemovedEvent.getRemovedEntry();
                    logger.info(String.format("CircuitBreaker %s removed", removedCircuitBreaker.getName()));
                });

        circuitBreakerRegistry.circuitBreaker("name1");
        circuitBreakerRegistry.remove("name1");
    }

    private static void consumeCircuitBreakerEvents() {
        CircuitBreaker circuitBreaker = CircuitBreaker
                .ofDefaults("testName");

        circuitBreaker.getEventPublisher()
                .onSuccess(event -> logger.info("success"))
                .onError(event -> logger.info("error"))
                .onIgnoredError(event -> logger.info("ignored error"))
                .onReset(event -> logger.info("reset"))
                .onStateTransition(event -> logger.info("state transition"));

        // Or if you want to register a consumer listening to all events, you can do:
        circuitBreaker.getEventPublisher()
                .onEvent(event -> logger.info("all events msg"));
    }

    private static void consumeCircuitBreakerCircularEvents() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        CircularEventConsumer<CircuitBreakerEvent> ringBuffer = new CircularEventConsumer<>(10);
        circuitBreaker.getEventPublisher().onEvent(ringBuffer);
        List<CircuitBreakerEvent> bufferedEvents = ringBuffer.getBufferedEvents();
    }

    private static void transitionToStatesManually() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");
        circuitBreaker.transitionToDisabledState();
        // circuitBreaker.onFailure(...) won't trigger a state change
        circuitBreaker.transitionToClosedState(); // will transition to CLOSED state and re-enable normal behaviour, keeping metrics
        circuitBreaker.transitionToForcedOpenState();
        // circuitBreaker.onSuccess(...) won't trigger a state change
        circuitBreaker.reset(); //  will transition to CLOSED state and re-enable normal behaviour, losing metrics
    }
}
