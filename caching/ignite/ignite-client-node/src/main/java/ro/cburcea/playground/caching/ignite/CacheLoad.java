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
        IgniteCache<Integer, Integer> cache = ignite.getOrCreateCache("myCache");

        final int key = new Random().nextInt();
        final int val = new Random().nextInt();
        cache.put(key, val);
        log.info("result from cache: key={},val= {}", key, val);
    }
}

