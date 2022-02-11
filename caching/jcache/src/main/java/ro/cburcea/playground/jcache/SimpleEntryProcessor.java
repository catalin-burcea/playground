package ro.cburcea.playground.jcache;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import java.io.Serializable;

public class SimpleEntryProcessor implements EntryProcessor<Integer, Integer, Integer>, Serializable {

    public Integer process(MutableEntry<Integer, Integer> entry, Object... args)
            throws EntryProcessorException {

        if (entry.exists()) {
            Integer current = entry.getValue();
            entry.setValue(current + 1);
            return current;
        }
        return null;
    }
}