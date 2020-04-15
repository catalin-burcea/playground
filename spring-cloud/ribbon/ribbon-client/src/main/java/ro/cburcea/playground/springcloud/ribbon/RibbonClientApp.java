package ro.cburcea.playground.springcloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RibbonClientApp {

    public static void main(String[] args) {
        SpringApplication.run(RibbonClientApp.class, args);
    }
}