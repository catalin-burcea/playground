import org.junit.Before;
import org.junit.Test;
import ro.cburcea.playground.jcache.SimpleCacheLoader;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import static org.junit.Assert.assertEquals;

public class CacheLoaderTest {

    private Cache<Integer, String> cache;

    @Before
    public void setup() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<Integer, String> config = new MutableConfiguration<>();

        config.setReadThrough(true)
                .setCacheLoaderFactory(new FactoryBuilder.SingletonFactory<>(new SimpleCacheLoader()));

        this.cache = cacheManager.createCache("SimpleCache", config);
    }

    @Test
    public void whenReadingFromStorage_thenCorrect() {
        int size = 5;
        int next = size + 1;
        cache.put(next, "ad-hoc:" + next);
        for (int i = 1; i < size; i++) {
            String value = cache.get(i);

            assertEquals("fromCacheLoader:" + i, value);
        }
        assertEquals("ad-hoc:" + next, cache.get(next));
    }
}