package ro.cburcea.playground.libraries.redis.redisson;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
public class BookLiveObject {

    @RId
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}