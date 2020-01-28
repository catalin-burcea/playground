package com.cburcea.playground.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyExamples {

    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        if(!list.contains("foo")) {
            list.add("foo");
        }

        Map<String, String> map = new ConcurrentHashMap<>();
        map.putIfAbsent("foo", "bar");
        map.computeIfAbsent("foo2", key -> key + "bar");
        System.out.println(map);


        AtomicInteger atomicInteger = new AtomicInteger(3);
        atomicInteger.incrementAndGet();
        atomicInteger.compareAndSet(4,6);
        System.out.println(atomicInteger.incrementAndGet());
    }
}
