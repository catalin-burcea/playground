package ro.cburcea.playground.libraries.redis.redisson;

import java.util.List;


public interface BookServiceInterface {
    List<String> getEntries(int count);
}