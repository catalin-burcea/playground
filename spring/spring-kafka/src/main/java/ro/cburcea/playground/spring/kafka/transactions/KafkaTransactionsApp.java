package ro.cburcea.playground.spring.kafka.transactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class KafkaTransactionsApp {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaTransactionsApp.class);

    @Autowired
    private TransactionalWordCount transactionalWordCount;

    public static void main(String[] args) {
        SpringApplication.run(KafkaTransactionsApp.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEventListener() {
        LOG.info("EXECUTING : eventListener");
        transactionalWordCount.process();
    }
}
