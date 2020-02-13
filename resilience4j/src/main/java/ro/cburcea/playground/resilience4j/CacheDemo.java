package ro.cburcea.playground.resilience4j;

import io.github.resilience4j.cache.Cache;
import io.github.resilience4j.decorators.Decorators;
import io.vavr.CheckedFunction1;
import io.vavr.control.Try;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import java.util.logging.Logger;

public class CacheDemo {

    private static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        createAndConfigureCache();
    }

    private static void createAndConfigureCache() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        javax.cache.Cache<String, String> cacheInstance = cacheManager
                .createCache("cacheName", new MutableConfiguration<>());

        Cache<String, String> cacheContext = Cache.of(cacheInstance);

        CheckedFunction1<String, String> cachedFunction = Decorators
                .ofCheckedSupplier(() -> {
                    logger.info("inside supplier");
                    return "result";
                })
                .withCache(cacheContext)
                .decorate();

        String value1 = Try.of(() -> cachedFunction.apply("cacheKey")).get();
        // the second value is returned from cache
        String value2 = Try.of(() -> cachedFunction.apply("cacheKey")).get();

        logger.info(value1);
        logger.info(value2);
    }
}
