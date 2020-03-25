package ro.cburcea.playground.jcache;

import javax.cache.Cache;

public class SquaredCalculator {

    public static final String SQUARED_NUMBER_CACHE = "squaredNumber";
    private Cache<Integer, Integer> cache;

    SquaredCalculator() {
        cache = CacheHelper.getCache(SQUARED_NUMBER_CACHE);
    }

    public int getSquareValueOfNumber(int input) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }

        System.out.println(String.format("Calculating square value of %s and caching result.", input));

        int squaredValue = (int) Math.pow(input, 2);
        cache.put(input, squaredValue);

        return squaredValue;
    }

    public int getIncrementedSquareValueOfNumber(int input) {
        if (cache.containsKey(input)) {
            cache.invoke(input, new SimpleEntryProcessor());
            return cache.get(input);
        }

        System.out.println(String.format("Calculating square value of %s and caching result.", input));

        int squaredValue = (int) Math.pow(input, 2);
        cache.put(input, squaredValue);

        return squaredValue;
    }

}