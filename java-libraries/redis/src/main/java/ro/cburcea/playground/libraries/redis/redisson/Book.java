package ro.cburcea.playground.libraries.redis.redisson;

import java.io.Serializable;

public class Book implements Serializable {

    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}