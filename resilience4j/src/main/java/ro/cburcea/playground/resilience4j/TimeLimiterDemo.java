package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class TimeLimiterDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws Exception {
        cancelRunningFuture();
    }

    private static void createAndConfigureATimeLimiter() {
        TimeLimiterConfig config1 = TimeLimiterConfig.custom()
                .cancelRunningFuture(true)
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        // Create a TimeLimiterRegistry with a custom global configuration
        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.of(config1);

        // Get or create a TimeLimiter from the registry -
        // TimeLimiter will be backed by the default config
        TimeLimiter timeLimiterWithDefaultConfig = timeLimiterRegistry.timeLimiter("name1");

        // Get or create a Retry from the registry,
        // use a custom configuration when creating the retry
        TimeLimiterConfig config2 = TimeLimiterConfig.custom()
                .cancelRunningFuture(false)
                .timeoutDuration(Duration.ofMillis(1000))
                .build();

        TimeLimiter timeLimiterWithCustomConfig = timeLimiterRegistry.timeLimiter("name2", config2);
    }

    private static void decorateAFunctionalInterface() throws Exception {
        // Create a TimeLimiter
        TimeLimiter timeLimiter = TimeLimiter.of(Duration.ofSeconds(1));
        // The Scheduler is needed to schedule a timeout on a non-blocking CompletableFuture
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        // The non-blocking variant with a CompletableFuture
        CompletableFuture<String> result1 = timeLimiter
                .executeCompletionStage(scheduler, () -> CompletableFuture.supplyAsync(() -> "hello1"))
                .toCompletableFuture();

        // The blocking variant which is basically future.get(timeoutDuration, MILLISECONDS)
        String result2 = timeLimiter
                .executeFutureSupplier(() -> CompletableFuture.supplyAsync(() -> "hello2"));

    }

    private static void cancelRunningFuture() throws Exception {
        TimeLimiterConfig config1 = TimeLimiterConfig.custom()
                .cancelRunningFuture(false)
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        TimeLimiter timeLimiter = TimeLimiter.of("name1", config1);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        // The non-blocking variant with a CompletableFuture
        CompletableFuture<String> result = timeLimiter
                .executeCompletionStage(scheduler, () -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                    logger.info("returning successfully from service");
                    return "hello1";
                }))
                .toCompletableFuture();


        // The blocking variant which is basically future.get(timeoutDuration, MILLISECONDS)
        String result2 = timeLimiter
                .executeFutureSupplier(() -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                    logger.info("returning successfully from service2");
                    return "hello2";
                }));

        logger.info(result.get());
        logger.info(result2);

    }
}
