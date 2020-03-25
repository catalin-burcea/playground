package ro.cburcea.playground.jcache;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import java.io.Serializable;

public class GlobalCacheEntryListener implements
        CacheEntryCreatedListener<Integer, Integer>,
        CacheEntryUpdatedListener<Integer, Integer>,
        Serializable {

    public void onUpdated(Iterable<CacheEntryEvent<? extends Integer,
                    ? extends Integer>> events) throws CacheEntryListenerException {
        events.forEach(event -> System.out.println(String.format("the cache entry with key %s was updated to %s", event.getKey(), event.getValue())));
    }

    public void onCreated(Iterable<CacheEntryEvent<? extends Integer,
                    ? extends Integer>> events) throws CacheEntryListenerException {
        events.forEach(event -> System.out.println(String.format("the cache entry with key %s was created", event.getKey())));

    }

}