package ro.cburcea.playground.spring.kafka.streams;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStreamsApp {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsApp.class, args);
    }
}
