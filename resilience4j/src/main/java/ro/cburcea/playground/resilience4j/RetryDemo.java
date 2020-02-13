package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class RetryDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        decorateAndExecuteAFunctionalInterface();
    }

    private static void retryConfiguration() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(2)
                .waitDuration(Duration.ofMillis(1000))
                .retryOnResult(response -> response.equals(500))
                .retryOnException(e -> e instanceof WebServiceException)
                .retryExceptions(IOException.class, TimeoutException.class)
                .ignoreExceptions(NullPointerException.class) //for testing purposes
                .build();

        // Create a RetryRegistry with a custom global configuration
        RetryRegistry registry = RetryRegistry.of(config);

        // Get or create a Retry from the registry -
        // Retry will be backed by the default config
        Retry retryWithDefaultConfig = registry.retry("name1");

        // Get or create a Retry from the registry,
        // use a custom configuration when creating the retry
        RetryConfig custom = RetryConfig.custom()
                .waitDuration(Duration.ofMillis(100))
                .build();

        Retry retryWithCustomConfig = registry.retry("name2", custom);
    }

    private static void decorateAndExecuteAFunctionalInterface() {
        Retry retry = Retry.ofDefaults("id");

        CheckedFunction0<String> retryableSupplier = Retry
                .decorateCheckedSupplier(retry, () -> {throw new RuntimeException();});

        Try<String> result = Try.of(retryableSupplier)
                .recover((throwable) -> "Hello world from recovery function");

        logger.info(result.get());
    }

    private static void useACustomIntervalFunction() {
        IntervalFunction defaultWaitInterval = IntervalFunction
                .ofDefaults();

        // This interval function is used internally
        // when you only configure waitDuration
        IntervalFunction fixedWaitInterval = IntervalFunction
                .of(Duration.ofSeconds(5));

        IntervalFunction intervalWithExponentialBackoff = IntervalFunction
                .ofExponentialBackoff();

        IntervalFunction intervalWithCustomExponentialBackoff = IntervalFunction
                .ofExponentialBackoff(IntervalFunction.DEFAULT_INITIAL_INTERVAL, 2d);

        IntervalFunction randomWaitInterval = IntervalFunction
                .ofRandomized();

        // Overwrite the default intervalFunction with your custom one
        RetryConfig retryConfig = RetryConfig.custom()
                .intervalFunction(intervalWithExponentialBackoff)
                .build();
    }
}
