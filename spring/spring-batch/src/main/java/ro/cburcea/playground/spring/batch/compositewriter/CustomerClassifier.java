package ro.cburcea.playground.spring.batch.compositewriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

public class CustomerClassifier implements Classifier<Customer, ItemWriter> {

    private static final long serialVersionUID = 1L;

    private ItemWriter evenItemWriter;
    private ItemWriter oddItemWriter;

    public CustomerClassifier(ItemWriter evenItemWriter, ItemWriter oddItemWriter) {
        this.evenItemWriter = evenItemWriter;
        this.oddItemWriter = oddItemWriter;
    }

    @Override
    public ItemWriter classify(Customer customer) {
        return customer.getId() % 2 == 0 ? evenItemWriter : oddItemWriter;
    }
}