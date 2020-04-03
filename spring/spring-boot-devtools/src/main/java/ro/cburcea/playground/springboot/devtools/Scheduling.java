package ro.cburcea.playground.springboot.devtools;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduling {

    @Scheduled(fixedRate = 2000)
    public void job() {
        System.out.println("executing a job..ss.");
    }
}
