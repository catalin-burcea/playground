package ro.cburcea.playground.spring.batch.conditional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConditionalFlowApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConditionalFlowApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConditionalFlowApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("Running conditional flow application...");
    }
}