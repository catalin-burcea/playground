package ro.cburcea.playground.jcache;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.HashMap;
import java.util.Map;

public class SimpleCacheLoader implements CacheLoader<Integer, String> {

    public String load(Integer key) throws CacheLoaderException {
        return "fromCacheLoader:" + key;
    }

    public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
        Map<Integer, String> data = new HashMap<>();
        for (int key : keys) {
            data.put(key, load(key));
        }
        return data;
    }
}