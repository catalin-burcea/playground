package ro.cburcea.playground.caching.ignite;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheLoad {

    private final Ignite ignite;

    @PostConstruct
    public void load() {
        IgniteCache<Integer, Integer> myCache = ignite.getOrCreateCache("myCache");
        IgniteCache<Integer, Integer> myNearCache = ignite.getOrCreateCache("myNearCache");

        final int key = new Random().nextInt();
        final int val = new Random().nextInt();
        myCache.put(key, val);
        myNearCache.put(key, val);

//        NearCacheConfiguration<Integer, String> nearCfg = new NearCacheConfiguration<>();
//        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(100_000));
//        IgniteCache<Integer, String> myLocalNearCache = ignite.getOrCreateNearCache("myLocalNearCache", nearCfg);
//        myLocalNearCache.put(key, "2");

        log.info("result from myCache: key={}, val={}", key, myCache.get(key));
        log.info("result from myNearCache: key={}, val={}", key, myNearCache.get(key));
//        log.info("result from myLocalNearCache: key={}, val={}", key, myLocalNearCache.get(key));
    }
}

