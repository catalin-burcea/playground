package ro.cburcea.playground.spring.batch.conditional;

import org.springframework.batch.item.ItemProcessor;

public class NumberInfoClassifierWithDecider implements ItemProcessor<NumberInfo, Integer> {

    @Override
    public Integer process(NumberInfo numberInfo) {
        return numberInfo.getNumber();
    }
}