package ro.cburcea.playground.libraries.redis.redisson;

import java.util.Arrays;
import java.util.List;


public class BookServiceImpl implements BookServiceInterface {

    String[] returnArray = {"entry1", "entry2", "entry3"};

    @Override
    public List<String> getEntries(int count) {
        return Arrays.asList(returnArray);
    }
}