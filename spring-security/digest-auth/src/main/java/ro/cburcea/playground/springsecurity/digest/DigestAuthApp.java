package ro.cburcea.playground.springsecurity.digest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigestAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(DigestAuthApp.class, args);
    }
}
