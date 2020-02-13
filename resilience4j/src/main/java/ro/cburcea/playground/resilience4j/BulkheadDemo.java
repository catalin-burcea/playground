package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.bulkhead.*;

import java.time.Duration;
import java.util.logging.Logger;

public class BulkheadDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        consumeEmittedRegistryEvents();
    }

    private static void createASemaphoreBulkheadWithDefaultConfiguration() {
        BulkheadRegistry registry = BulkheadRegistry.ofDefaults();

        // Get or create a Bulkhead from the registry -
        // bulkhead will be backed by the default config
        Bulkhead bulkheadWithDefaultConfig = registry.bulkhead("name1");
    }

    private static void createASemaphoreBulkHeadWithCustomConfiguration() {
        // Create a custom configuration for a Bulkhead
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(150)
                .maxWaitDuration(Duration.ofMillis(500))
                .build();

        // Create a BulkheadRegistry with a custom global configuration
        BulkheadRegistry registry = BulkheadRegistry.of(config);

        // Get or create a Bulkhead from the registry,
        // use a custom configuration when creating the bulkhead
        Bulkhead bulkheadWithCustomConfig = registry.bulkhead("name2", config);
    }

    private static void createAThreadPoolBulkheadWithCustomConfiguration() {
        ThreadPoolBulkheadConfig config = ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(10)
                .coreThreadPoolSize(2)
                .queueCapacity(20)
                .build();

        // Create a BulkheadRegistry with a custom global configuration
        ThreadPoolBulkheadRegistry registry = ThreadPoolBulkheadRegistry.of(config);

        // Get or create a ThreadPoolBulkhead from the registry -
        // bulkhead will be backed by the default config
        ThreadPoolBulkhead bulkheadWithDefaultConfig = registry.bulkhead("name1");

        // Get or create a Bulkhead from the registry,
        // use a custom configuration when creating the bulkhead
        ThreadPoolBulkheadConfig custom = ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(5)
                .build();

        ThreadPoolBulkhead bulkheadWithCustomConfig = registry.bulkhead("name2", custom);
    }

    private static void consumeEmittedRegistryEvents() {
        BulkheadRegistry registry = BulkheadRegistry.ofDefaults();
        registry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    Bulkhead addedBulkhead = entryAddedEvent.getAddedEntry();
                    logger.info(String.format("Bulkhead %s added", addedBulkhead.getName()));
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    Bulkhead removedBulkhead = entryRemovedEvent.getRemovedEntry();
                    logger.info(String.format("Bulkhead %s removed", removedBulkhead.getName()));
                });

        registry.bulkhead("test");
        registry.remove("test");
    }

    private static void consumeEmittedBulkheadEvents() {
        Bulkhead bulkhead = Bulkhead.ofDefaults("test");

        bulkhead.getEventPublisher()
                .onCallPermitted(event -> logger.info("permitted"))
                .onCallRejected(event -> logger.info("rejected"))
                .onCallFinished(event -> logger.info("finished"));
    }
}
