package ro.cburcea.playground.docker.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloDockerApp {

    public static void main(String[] args) {
        SpringApplication.run(HelloDockerApp.class, args);
    }
}
