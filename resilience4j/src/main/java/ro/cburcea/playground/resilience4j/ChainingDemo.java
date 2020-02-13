package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.logging.Logger;

public class ChainingDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        chainMultipleFunctions();
    }

    private static void chainMultipleFunctions() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");
        // Create a custom configuration for a Bulkhead
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(1)
                .maxWaitDuration(Duration.ofMillis(1))
                .build();
        Bulkhead bulkhead = Bulkhead.of("testName", config);
        RateLimiter rateLimiter = RateLimiter.ofDefaults("testName");

        CheckedFunction0<String> stringCheckedFunction0 = () -> {
            Thread.sleep(1000);
            return "success";
        };

//        CheckedFunction0<String> decoratedSupplier = CircuitBreaker
//                .decorateCheckedSupplier(circuitBreaker, stringCheckedFunction0);

        CheckedFunction0<String> decoratedSupplier2 = Bulkhead
                .decorateCheckedSupplier(bulkhead, stringCheckedFunction0);

        CheckedFunction0<String> decoratedSupplier3 = RateLimiter
                .decorateCheckedSupplier(rateLimiter, decoratedSupplier2);

        Try<String> result = Try.of(decoratedSupplier3)
                .recover(throwable -> "Hello from Recovery");
        Try<String> result2 = Try.of(decoratedSupplier3)
                .recover(throwable -> "Hello from Recovery");
        Try<String> result3 = Try.of(decoratedSupplier3)
                .recover(throwable -> "Hello from Recovery");

        logger.info(result.get());
        logger.info(result2.get());
        logger.info(result3.get());
    }
}
