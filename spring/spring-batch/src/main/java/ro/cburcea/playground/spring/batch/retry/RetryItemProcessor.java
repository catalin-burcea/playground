package ro.cburcea.playground.spring.batch.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;


public class RetryItemProcessor implements ItemProcessor<Transaction, Transaction> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryItemProcessor.class);

    public Transaction process(Transaction transaction) {

        //simulating a random exception
        if (new Random().nextInt(10) % 4 == 0) {
            LOGGER.error("throwing exception for transaction: {}", transaction.getId());
            throw new RuntimeException();
        }
        return transaction;
    }
}