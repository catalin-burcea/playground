package ro.cburcea.playground.jcache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

public class CacheHelper {

    private static CachingProvider cachingProvider = Caching.getCachingProvider();
    private static CacheManager cacheManager = cachingProvider.getCacheManager();

    private static MutableConfiguration<Integer, Integer> getCacheConfiguration() {
        CacheEntryListenerConfiguration<Integer, Integer> listenerConfig = new MutableCacheEntryListenerConfiguration<>(
                FactoryBuilder.factoryOf(GlobalCacheEntryListener.class), null, false, true);

        MutableConfiguration<Integer, Integer> config = new MutableConfiguration<>();
        config.setTypes(Integer.class, Integer.class);
        config.addCacheEntryListenerConfiguration(listenerConfig);
        config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        return config;
    }

    public static Cache<Integer, Integer> getCache(String name) {
        Cache<Integer, Integer> cache = cacheManager.getCache(name, Integer.class, Integer.class);
        if(cache == null) {
            MutableConfiguration<Integer, Integer> config = getCacheConfiguration();
            cache = cacheManager.createCache(name, config);
        }
        return cache;
    }
}
  