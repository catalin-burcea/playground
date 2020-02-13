package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.logging.Logger;

public class RateLimiterDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        consumeEmittedRegistryEvents();
    }

    private static void restrictNrOfRequest() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofMillis(25))
                .build();

        // Create registry
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        // Use registry
        RateLimiter rateLimiterWithDefaultConfig = rateLimiterRegistry
                .rateLimiter("name1");

        RateLimiter rateLimiterWithCustomConfig = rateLimiterRegistry
                .rateLimiter("name2", config);
    }

    private static void decorateAFunctionalInterface() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(1)
                .timeoutDuration(Duration.ofMillis(1))
                .build();

        // Create registry
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        RateLimiter rateLimiterWithCustomConfig = rateLimiterRegistry
                .rateLimiter("name2", config);

        // Decorate your call to BackendService.doSomething()
        CheckedRunnable restrictedCall = RateLimiter
                .decorateCheckedRunnable(rateLimiterWithCustomConfig, () -> logger.info("do something"));

        Try.run(restrictedCall)
                .andThenTry(restrictedCall)
                .andThenTry(restrictedCall)
                .andThenTry(restrictedCall)
                .onFailure((throwable) -> logger.info("Wait before call it again :)"));
    }

    private static void consumeEmittedRegistryEvents() {
        RateLimiterRegistry registry = RateLimiterRegistry.ofDefaults();
        registry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    RateLimiter addedRateLimiter = entryAddedEvent.getAddedEntry();
                    logger.info(String.format("RateLimiter %s added", addedRateLimiter.getName()));
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    RateLimiter removedRateLimiter = entryRemovedEvent.getRemovedEntry();
                    logger.info(String.format("RateLimiter %s removed", removedRateLimiter.getName()));
                });

        registry.rateLimiter("test");
        registry.remove("test");
    }

    private static void consumeEmittedRateLimiterEvents() {
        RateLimiter rateLimiter = RateLimiter.ofDefaults("test");

        rateLimiter.getEventPublisher()
                .onSuccess(event -> logger.info("success"))
                .onFailure(event -> logger.info("failure"));

    }
}
