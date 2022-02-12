package ro.cburcea.playground.caching.ignite;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IgniteClientNode {

    public static void main(String[] args) {
        SpringApplication.run(IgniteClientNode.class, args);
    }
}
