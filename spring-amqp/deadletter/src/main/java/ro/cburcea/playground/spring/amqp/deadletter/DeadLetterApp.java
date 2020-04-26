package ro.cburcea.playground.spring.amqp.deadletter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DeadLetterApp {

    @Autowired
    MessageProducer messageProducer;

    public static void main(String[] args) {
        SpringApplication.run(DeadLetterApp.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        messageProducer.sendMessage();
    }
}